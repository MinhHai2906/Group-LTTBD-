package com.example.wateronl

import androidx.compose.runtime.mutableStateListOf

object GioHangData {
    val danhSachSanPham = mutableStateListOf<ThanhPhanUi>()

    fun themVaoGio(sanPham: ThanhPhanUi) {
        // Tìm xem sản phẩm đã có trong giỏ chưa (dựa vào tên)
        val index = danhSachSanPham.indexOfFirst { it.namedrink == sanPham.namedrink }
        if (index != -1) {
            // Nếu có rồi, lấy sản phẩm cũ ra, cộng dồn số lượng mới vào
            val sanPhamCu = danhSachSanPham[index]
            val sanPhamMoi = sanPhamCu.copy(increasing = sanPhamCu.increasing + sanPham.increasing)
            // Cập nhật lại vào danh sách
            danhSachSanPham[index] = sanPhamMoi
        } else {
            // Nếu chưa có, thêm mới hoàn toàn
            danhSachSanPham.add(sanPham)
        }
    }
    
    fun xoaKhoiGio(sanPham: ThanhPhanUi) {
        danhSachSanPham.remove(sanPham)
    }
    
    fun tinhTongTien(): Int {
        // Tính tổng tiền = giá * số lượng của từng món
        return danhSachSanPham.sumOf { it.price * it.increasing }
    }

    // Hàm dùng cho trang Giỏ Hàng: Tăng/Giảm trực tiếp
    fun capNhatSoLuong(sanPham: ThanhPhanUi, soLuongMoi: Int) {
        val index = danhSachSanPham.indexOf(sanPham)
        if (index != -1) {
            if (soLuongMoi > 0) {
                danhSachSanPham[index] = sanPham.copy(increasing = soLuongMoi)
            } else {
                // Nếu giảm về 0 hoặc nhỏ hơn, có thể hỏi người dùng có muốn xóa không
                // Ở đây mình chọn cách xóa luôn cho tiện
                danhSachSanPham.removeAt(index)
            }
        }
    }
}
