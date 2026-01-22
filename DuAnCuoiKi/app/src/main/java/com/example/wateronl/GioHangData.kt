package com.example.wateronl

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.auth.FirebaseAuth

object GioHangData {
    val danhSachSanPham = mutableStateListOf<ThanhPhanUi>()
    private var prefs: SharedPreferencesManager? = null
    private val currentUid: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: "khach_vang_lai"

    fun init(context: Context) {
        prefs = SharedPreferencesManager(context)
        FirebaseAuth.getInstance().addAuthStateListener {
            Log.d("GIO_HANG", "Phát hiện đổi tài khoản -> Tải lại giỏ hàng cho: $currentUid")
            taiLaiGioHang()
        }
    }

    fun taiLaiGioHang() {
        // 1. Lấy dữ liệu của người dùng hiện tại
        val dataCu = prefs?.taiDanhSachSanPham(currentUid) ?: emptyList()

        // 2. Xóa sạch dữ liệu cũ đang hiển thị trên màn hình
        danhSachSanPham.clear()

        // 3. Nạp dữ liệu mới vào
        danhSachSanPham.addAll(dataCu)
    }

    private fun luuDuLieu() {
        prefs?.luuDanhSachSanPham(currentUid, danhSachSanPham.toList())
    }

    fun themVaoGio(sanPham: ThanhPhanUi) {
        val index = danhSachSanPham.indexOfFirst { it.namedrink == sanPham.namedrink }
        if (index != -1) {
            val sanPhamCu = danhSachSanPham[index]
            val sanPhamMoi = sanPhamCu.copy(increasing = sanPhamCu.increasing + sanPham.increasing)
            danhSachSanPham[index] = sanPhamMoi
        } else {
            danhSachSanPham.add(0, sanPham)
        }
        luuDuLieu()
    }

    fun xoaKhoiGio(sanPham: ThanhPhanUi) {
        danhSachSanPham.remove(sanPham)
        luuDuLieu()
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
            luuDuLieu()
        }
    }
}