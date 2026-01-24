package com.example.wateronl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Composable
fun LichSuDonHang(
    navController: NavController,
    hienNutBack: Boolean = true,        // dùng để ẩn nút back nếu màn hình này nằm trong Bottom Bar
    onItemClick: (String) -> Unit       // Callback trả về ID đơn hàng khi người dùng nhấn vào
) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser         // Lấy thông tin người dùng hiện tại để lọc đơn hàng

    // mutableStateListOf: Danh sách đặc biệt, khi dữ liệu bên trong thay đổi, giao diện sẽ tự vẽ lại
    val danhSachDonHang = remember { mutableStateListOf<DonHang>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = user) {
        if (user != null) {
            db.collection("don_hang")
                .whereEqualTo("uid", user.uid)  // Chỉ lấy đơn hàng của chính người dùng này
                .orderBy("timestamp", Query.Direction.DESCENDING)   // Sắp xếp: Đơn mới nhất hiện lên đầu
                .addSnapshotListener { snapshots, e ->        // Lắng nghe dữ liệu thời gian thực (Realtime)
                    if (e != null) {
                        isLoading = false; return@addSnapshotListener
                    }
                    if (snapshots != null) {
                        danhSachDonHang.clear()                // Xóa danh sách cũ để cập nhật danh sách mới nhất
                        for (doc in snapshots) {
                            val donHang = doc.toObject(DonHang::class.java)
                            if (donHang.timestamp == 0L) donHang.timestamp =
                                System.currentTimeMillis()
                            danhSachDonHang.add(donHang)
                        }
                    }
                    isLoading = false
                }
        } else {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color(0xFFF9F9F9))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            if (hienNutBack) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(50.dp)
                        .padding(start = 20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "back",
                        tint = MauCam,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Text(
                text = "Đơn hàng của tôi",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MauCam,
                textAlign = TextAlign.Center
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(      // Hiện vòng xoay khi đang tải dữ liệu từ Cloud Firestore
                    modifier = Modifier.align(Alignment.Center),
                    color = MauCam
                )
            } else if (danhSachDonHang.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Bạn chưa có đơn hàng nào", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(danhSachDonHang) { donHang ->
                        ItemDonHang(donHang, onClick = { onItemClick(donHang.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun ItemDonHang(donHang: DonHang, onClick: () -> Unit) {
    val mauTrangThai = when (donHang.trangThai) {
        0 -> Color(0xFFFF9800); 1 -> Color(0xFF2196F3); 2 -> Color(0xFF9C27B0)
        3 -> Color(0xFF4CAF50); 4 -> Color(0xFFF44336); else -> Color.Gray
    }
    val tenTrangThai = when (donHang.trangThai) {
        0 -> "Chờ xác nhận"; 1 -> "Đang pha chế"; 2 -> "Đang giao hàng"
        3 -> "Giao thành công"; 4 -> "Đã hủy"; else -> "Không xác định"
    }

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }        // Nhấn vào thẻ đơn hàng để xem chi tiết
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("#${donHang.id.takeLast(8)}", fontWeight = FontWeight.Bold, color = Color.Gray)
                Text(text = donHang.ngayDat, fontSize = 12.sp, color = Color.Gray)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            donHang.danhSachMon.take(2).forEach { item ->       //  chỉ hiển thị tối đa 2 món
                Text(
                    "${item.soLuong}x ${item.tenMon}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            if (donHang.danhSachMon.size > 2) {                     // nếu đơn hàng có nhiều hơn 2 món, hiển thị thêm dòng báo hiệu
                Text(
                    "... và ${donHang.danhSachMon.size - 2} món khác",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${donHang.tongTien}đ",     // hiển thị tổng tiền và tag trạng thái
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFFD84315)
                )
                Surface(
                    color = mauTrangThai.copy(alpha = 0.2f),
                    contentColor = mauTrangThai,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        tenTrangThai,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}