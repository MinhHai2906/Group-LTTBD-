package com.example.wateronl

import com.google.firebase.firestore.PropertyName

data class DonHang(
    var id: String = "",
    var uid: String = "",
    var tenNguoiNhan: String = "",
    var sdt: String = "",
    var diaChi: String = "",
    var ghiChu: String = "",
    var tongTien: Long = 0,
    var phuongThuc: String = "",
    @get:PropertyName("daThanhToan")
    var daThanhToan: Boolean = false,
    var trangThai: Int = 0,
    var ngayDat: String = "",
    var timestamp: Long = System.currentTimeMillis(),
    var danhSachMon: List<ChiTietDonHang> = emptyList()
)

data class ChiTietDonHang(
    var tenMon: String = "",
    var imageId: Int = 0,
    var soLuong: Int = 0,
    var gia: Int = 0
)