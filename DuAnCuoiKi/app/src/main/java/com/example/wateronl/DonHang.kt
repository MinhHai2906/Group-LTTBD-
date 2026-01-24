package com.example.wateronl

import com.google.firebase.firestore.PropertyName

data class DonHang(     // Data class đại diện cho một Đơn hàng trong hệ thống
    var id: String = "",            // ID duy nhất của đơn hàng (lấy từ Document ID của Firestore)
    var uid: String = "",           // ID của người dùng đã đặt đơn hàng này
    var tenNguoiNhan: String = "",
    var sdt: String = "",
    var diaChi: String = "",
    var ghiChu: String = "",
    var tongTien: Long = 0,
    var phuongThuc: String = "",
    @get:PropertyName("daThanhToan")             // Đảm bảo Firebase hiểu đúng tên field khi ánh xạ dữ liệu
    var daThanhToan: Boolean = false,
    var trangThai: Int = 0,
    var ngayDat: String = "",
    var timestamp: Long = System.currentTimeMillis(),   // Thời gian tính theo mili giây
    var danhSachMon: List<ChiTietDonHang> = emptyList()
)

data class ChiTietDonHang(      // Data class bổ trợ để lưu chi tiết từng món trong một đơn hàng
    var tenMon: String = "",
    var imageId: Int = 0,
    var soLuong: Int = 0,
    var gia: Int = 0
)