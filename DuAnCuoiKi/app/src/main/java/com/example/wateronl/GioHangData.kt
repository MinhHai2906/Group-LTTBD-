package com.example.wateronl

import android.content.Context
import androidx.compose.runtime.mutableStateListOf

object GioHangData {
    val danhSachSanPham = mutableStateListOf<ThanhPhanUi>()
    private var sharedPreferencesManager: SharedPreferencesManager? = null

    fun init(context: Context) {
        sharedPreferencesManager = SharedPreferencesManager(context)
        danhSachSanPham.clear()
        danhSachSanPham.addAll(sharedPreferencesManager!!.taiDanhSachSanPham())
    }

    fun themVaoGio(sanPham: ThanhPhanUi) {
        val index = danhSachSanPham.indexOfFirst { it.namedrink == sanPham.namedrink }
        if (index != -1) {
            // Nếu đã có:
            // 1. Cập nhật số lượng
            val sanPhamCu = danhSachSanPham[index]
            val sanPhamMoi = sanPhamCu.copy(increasing = sanPhamCu.increasing + sanPham.increasing)

            // 2. Xóa vị trí cũ và đưa lên đầu để người dùng thấy mới nhất
            danhSachSanPham.removeAt(index)
            danhSachSanPham.add(0, sanPhamMoi)
        } else {
            // Nếu chưa có, thêm vào đầu danh sách (vị trí 0)
            danhSachSanPham.add(0, sanPham)
        }
        luuGioHang()
    }

    fun xoaKhoiGio(sanPham: ThanhPhanUi) {
        danhSachSanPham.remove(sanPham)
        luuGioHang()
    }

    fun tinhTongTien(): Int {
        return danhSachSanPham.sumOf { it.price * it.increasing }
    }

    fun capNhatSoLuong(sanPham: ThanhPhanUi, soLuongMoi: Int) {
        val index = danhSachSanPham.indexOf(sanPham)
        if (index != -1) {
            if (soLuongMoi > 0) {
                danhSachSanPham[index] = sanPham.copy(increasing = soLuongMoi)
            } else {
                danhSachSanPham.removeAt(index)
            }
            luuGioHang()
        }
    }

    private fun luuGioHang() {
        sharedPreferencesManager?.luuDanhSachSanPham(danhSachSanPham.toList())
    }
}