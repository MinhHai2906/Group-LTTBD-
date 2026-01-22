package com.example.wateronl

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChiTietDonHang(navController: NavController, donHangId: String) {
    val db = FirebaseFirestore.getInstance()
    var donHang by remember { mutableStateOf<DonHang?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Tải thông tin đơn hàng từ ID
    LaunchedEffect(donHangId) {
        db.collection("don_hang").document(donHangId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    donHang = document.toObject(DonHang::class.java)
                    // Gán lại ID vì Firestore toObject không tự map ID document
                    donHang?.id = document.id
                }
                isLoading = false
            }
            .addOnFailureListener { isLoading = false }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Chi tiết đơn hàng",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MauCam)
            }
        } else if (donHang == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Không tìm thấy đơn hàng", color = Color.Gray)
            }
        } else {
            val dh = donHang!!
            val mauTrangThai = when (dh.trangThai) {
                0 -> Color(0xFFFF9800); 1 -> Color(0xFF2196F3); 2 -> Color(0xFF9C27B0)
                3 -> Color(0xFF4CAF50); 4 -> Color(0xFFF44336); else -> Color.Gray
            }
            val tenTrangThai = when (dh.trangThai) {
                0 -> "Chờ xác nhận"; 1 -> "Đang pha chế"; 2 -> "Đang giao hàng"
                3 -> "Giao thành công"; 4 -> "Đã hủy"; else -> "Không xác định"
            }

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. THÔNG TIN TRẠNG THÁI (ĐÃ SỬA GIAO DIỆN)
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Dòng 1: Mã đơn (To, đậm) và Ngày đặt (Nhỏ, xám) dàn đều 2 bên
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Mã đơn hàng",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "#${dh.id.takeLast(8).uppercase()}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        color = Color.Black
                                    )
                                }

                                Surface(
                                    color = Color(0xFFF0F0F0),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = dh.ngayDat,
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 4.dp
                                        )
                                    )
                                }
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                            // Dòng 2: Trạng thái
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Trạng thái: ", fontSize = 16.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = tenTrangThai,
                                    color = mauTrangThai,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }

                // 2. Thông tin người nhận
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, null, tint = MauCam)
                                Spacer(Modifier.width(8.dp))
                                Text("Địa chỉ nhận hàng", fontWeight = FontWeight.Bold)
                            }
                            HorizontalDivider(Modifier.padding(vertical = 8.dp))
                            ThongTinDong(Icons.Default.Person, dh.tenNguoiNhan)
                            ThongTinDong(Icons.Default.Phone, dh.sdt)
                            Text(
                                dh.diaChi,
                                modifier = Modifier.padding(start = 32.dp, top = 4.dp),
                                fontSize = 14.sp
                            )

                            if (dh.ghiChu.isNotEmpty()) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Ghi chú: ${dh.ghiChu}",
                                    modifier = Modifier.padding(start = 32.dp),
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                            }
                        }
                    }
                }

                // 3. Danh sách món
                item {
                    Text(
                        "Danh sách món",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                }
                items(dh.danhSachMon) { mon ->
                    ItemMonChiTiet(mon)
                }

                // 4. Tổng tiền
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Phương thức thanh toán")
                                Text(
                                    if (dh.phuongThuc == "Thanh toán chuyển khoản") "ZaloPay/CK" else "Tiền mặt",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Trạng thái thanh toán")
                                Text(
                                    if (dh.daThanhToan) "Đã thanh toán" else "Chưa thanh toán",
                                    color = if (dh.daThanhToan) Color(0xFF4CAF50) else Color.Red
                                )
                            }
                            HorizontalDivider(Modifier.padding(vertical = 12.dp))
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Tổng tiền", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    "${dh.tongTien}đ",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MauCam
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThongTinDong(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Text(text, fontSize = 14.sp)
    }
}

@Composable
fun ItemMonChiTiet(mon: ChiTietDonHang) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ảnh món (Nếu có Image ID thì hiển thị, tạm thời dùng placeholder nếu cần)
        if (mon.imageId != 0) {
            Image(
                painter = painterResource(id = mon.imageId),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            // Fallback nếu không có ảnh local (hoặc dùng AsyncImage nếu là URL)
            Box(Modifier
                .size(50.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp)))
        }

        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(mon.tenMon, fontWeight = FontWeight.Bold)
            Text("x${mon.soLuong}", color = Color.Gray, fontSize = 14.sp)
        }
        Text("${mon.gia * mon.soLuong}đ", fontWeight = FontWeight.SemiBold)
    }
}