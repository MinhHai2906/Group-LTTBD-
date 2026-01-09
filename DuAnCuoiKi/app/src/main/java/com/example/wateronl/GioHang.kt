package com.example.wateronl

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun GioHangScreen(onBackClick: () -> Unit){
    val duLieuGioHang = GioHangData.danhSachSanPham
    val sanPhamDaChon = remember { mutableStateListOf<ThanhPhanUi>() }

    // Lắng nghe sự thay đổi của duLieuGioHang và cập nhật lại sanPhamDaChon
    LaunchedEffect(duLieuGioHang.toList()) {
        // Tạo một danh sách mới chứa các sản phẩm đã được cập nhật số lượng
        val sanPhamDaChonMoi = mutableListOf<ThanhPhanUi>()
        sanPhamDaChon.forEach { spChon ->
            // Tìm sản phẩm tương ứng trong giỏ hàng để lấy số lượng mới nhất
            val sanPhamTrongGio = duLieuGioHang.find { it.namedrink == spChon.namedrink }
            if (sanPhamTrongGio != null) {
                sanPhamDaChonMoi.add(sanPhamTrongGio)
            }
        }
        // Cập nhật lại toàn bộ danh sách đã chọn
        sanPhamDaChon.clear()
        sanPhamDaChon.addAll(sanPhamDaChonMoi)
    }

    val tongTien = sanPhamDaChon.sumOf { it.price * it.increasing }

    // 3. Trạng thái của checkbox "Tất cả" phụ thuộc vào 2 danh sách trên
    val isCheckedAll = duLieuGioHang.isNotEmpty() && sanPhamDaChon.size == duLieuGioHang.size
    // --- Hết quản lý State mới ---

    Column(
        modifier=Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        // Header
        Box(
            modifier=Modifier.fillMaxWidth().padding(top=30.dp),
            contentAlignment = Alignment.Center
        ){
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.CenterStart).size(50.dp)
                    .padding(start = 20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back",
                    tint = MauCam,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterStart)
                )
            }

            Text(
                text="Giỏ hàng",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MauCam,
                textAlign = TextAlign.Center
            )
        }

        // Danh sách sản phẩm
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {
            items(duLieuGioHang, key = { it.namedrink }) { sanPham ->
                // Kiểm tra xem item này có trong danh sách đã chọn hay không
                val isItemSelected = sanPhamDaChon.contains(sanPham)

                ItemGioHang(
                    sanPham = sanPham,
                    isChecked = isItemSelected,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            sanPhamDaChon.add(sanPham)
                        } else {
                            sanPhamDaChon.remove(sanPham)
                        }
                    },
                    onDelete = {
                        GioHangData.xoaKhoiGio(sanPham)
                        sanPhamDaChon.remove(sanPham) // Đồng thời xóa khỏi danh sách đã chọn
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Footer
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .height(110.dp)
                ,
            shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top=20.dp, start = 20.dp,end=20.dp,bottom=6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tổng tiền",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Text(
                        text = "${tongTien}đ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Checkbox(
                            checked = isCheckedAll,
                            onCheckedChange = { shouldCheckAll ->
                                if (shouldCheckAll) {
                                    sanPhamDaChon.clear()
                                    sanPhamDaChon.addAll(duLieuGioHang)
                                } else {
                                    sanPhamDaChon.clear()
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MauCam,
                                uncheckedColor = Color.Gray,
                                checkmarkColor = Color.White
                            )
                        )
                        Text(text = "Tất cả", fontSize = 17.sp, color = Color.Gray)
                    }

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE59C54)),
                        shape = RoundedCornerShape(40.dp),
                    ) {
                        Text(
                            text = "Thanh toán",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemGioHang(
    sanPham: ThanhPhanUi,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F8)),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MauCam,
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                )
            )

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = sanPham.image,
                    contentDescription = sanPham.namedrink,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start
            ) {
                // Tên sản phẩm
                Text(
                    text = sanPham.namedrink,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1
                )
                
                // Giá sản phẩm
                Text(
                    text = "${sanPham.price}đ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MauCam
                )

                // Hàng chứa Tăng/Giảm số lượng và nút Xóa
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Cụm Tăng/Giảm
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { 
                                GioHangData.capNhatSoLuong(sanPham, sanPham.increasing - 1)
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_minus), contentDescription = "Trừ", tint = Color.Gray)
                        }

                        Text(
                            text = "${sanPham.increasing}", 
                            modifier = Modifier.padding(horizontal = 8.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = { 
                                GioHangData.capNhatSoLuong(sanPham, sanPham.increasing + 1)
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_plus),
                                contentDescription = "Cộng",
                                tint = MauCam)
                        }
                    }


                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Xóa",
                            tint = MauCam)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGioHang(){
    MaterialTheme {
        GioHangScreen(onBackClick = {})
    }
}
