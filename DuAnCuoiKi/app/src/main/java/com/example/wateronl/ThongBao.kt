package com.example.wateronl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// tạo Singleton: Quản lý trạng thái thông báo tập trung cho toàn app
object ThongBaoApp {
    // MutableStateFlow: Dòng dữ liệu lưu trạng thái thông báo (nội dung, loại thông báo)
    private val _trangThai = MutableStateFlow<ThongBaoState?>(null)
    val trangThai = _trangThai.asStateFlow()

    // Hàm gọi khi muốn hiện lỗi (hiện màu đỏ)
    fun hienLoi(noiDung: String) {
        _trangThai.value = ThongBaoState(noiDung, false)
    }

    // Hàm gọi khi muốn hiện thông báo thành công (hiện màu xanh)
    fun hienThanhCong(noiDung: String) {
        _trangThai.value = ThongBaoState(noiDung, true)
    }

    // Hàm để ẩn thông báo
    fun an() { _trangThai.value = null }
}

// Data class định nghĩa cấu trúc một thông báo
data class ThongBaoState(val noiDung: String, val isSuccess: Boolean)

@Composable
fun SnackbarThongBao() {
    // Theo dõi trạng thái từ ThongBaoApp, khi trangThai thay đổi, UI sẽ vẽ lại
    val trangThai by ThongBaoApp.trangThai.collectAsState()

    LaunchedEffect(trangThai) {
        if (trangThai != null) {
            delay(3000)     // Hiệu ứng tự động ẩn sau 3 giây
            ThongBaoApp.an()          // Sau đó gọi hàm ẩn
        }
    }

    // Nếu có thông báo thì mới vẽ giao diện lên màn hình
    if (trangThai != null) {
        // Logic chọn màu và icon dựa trên loại thông báo (Thành công/Lỗi)
        val mauNen = if (trangThai!!.isSuccess) Color(0xFF4CAF50) else Color(0xFFE53935)
        val icon = if (trangThai!!.isSuccess) Icons.Default.CheckCircle else Icons.Default.Error

        // Box bao ngoài giúp đặt thông báo ở phía trên cùng màn hình (TopCenter)
        Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp, start = 20.dp, end = 20.dp), contentAlignment = Alignment.TopCenter) {
            Row(
                modifier = Modifier
                    .background(mauNen, RoundedCornerShape(12.dp))
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                // Hiển thị nội dung thông báo
                Text(trangThai!!.noiDung, color = Color.White, fontSize = 14.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Medium)
            }
        }
    }
}