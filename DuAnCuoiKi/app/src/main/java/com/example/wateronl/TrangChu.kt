package com.example.wateronl

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

open class ThanhPhanUi(
    @DrawableRes val image: Int,
    val namedrink: String,
    val price: Int,
    @DrawableRes val minus: Int,
    val increasing: Int, // tăng dần
    @DrawableRes val plus: Int,
    @DrawableRes val shop: Int,
    val buy:String
)
open class NhomUI(
    @DrawableRes val imageDrink: Int,
    val nameMon: String,
    val danhSachThanhPhan: List<ThanhPhanUi>
)

@Composable
fun TrangChuContent() {
    var search by remember { mutableStateOf("") }
    var anhDangChon by remember { mutableStateOf<Int?>(null) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            shape = RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp),
            colors = CardDefaults.cardColors(
                containerColor = MauCam.copy(alpha = 0.2f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = { Text("Tìm kiếm sản phẩm", fontSize = 16.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = 40.dp)
                        .shadow(elevation = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        Icon(
                            Icons.Default.Search, contentDescription = null,
                            modifier = Modifier.size(38.dp).padding(end = 10.dp)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedBorderColor = Color(0xFFD5AD90),
                        unfocusedBorderColor = MauCam.copy(alpha = 0.2f)
                    )
                )

                val buttonList = listOf(
                    "Tất cả",
                    "Coffee",
                    "Cookies Đá Xay",
                    "Đá Xay",
                    "Yaourt",
                    "Soda",
                    "Nước ép"
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(buttonList) { nameDrink ->
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.height(50.dp)
                        ) {
                            Text(text = nameDrink, fontSize = 16.sp)
                        }
                    }
                }
            }
        } // Hết phần Header (Card)


        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f) // Chiếm phần không gian còn lại
        ) {
            val DuLieuDrink = listOf(
                NhomUI(
                    imageDrink = R.drawable.attcaffe,
                    nameMon = "Coffee",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            image = R.drawable.cfbacsiu,
                            namedrink = "Bạc sĩu",
                            price = 28000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.cfda,
                            namedrink = "Cà phê đá",
                            price = 25000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.cfmuoi,
                            namedrink = "Cà phê muối",
                            price = 33000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.cfsua,
                            namedrink = "Cà phê sữa",
                            price = 28000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        )
                    )
                ),//NhómUI cafe
                NhomUI(
                    imageDrink = R.drawable.attcaffe,
                    nameMon = "Cookies đá xay",
                    danhSachThanhPhan = listOf(
                    ThanhPhanUi(
                        image = R.drawable.cookiesdaudx,
                        namedrink = "Cookies Dâu",
                        price = 40000,
                        minus = R.drawable.ic_minus,
                        increasing = 1,
                        plus = R.drawable.ic_plus,
                        shop = R.drawable.ic_giohang,
                        buy = "Mua ngay"
                    ),
                    ThanhPhanUi(
                        image = R.drawable.cookieschocolatepepermintdx,
                        namedrink = "Cookies Chocolate Pepermint",
                        price = 45000,
                        minus = R.drawable.ic_minus,
                        increasing = 1,
                        plus = R.drawable.ic_plus,
                        shop = R.drawable.ic_giohang,
                        buy = "Mua ngay"
                    ),
                    ThanhPhanUi(
                        image = R.drawable.cookiesxoaidx,
                        namedrink = "Cookies Xoài",
                        price = 40000,
                        minus = R.drawable.ic_minus,
                        increasing = 1,
                        plus = R.drawable.ic_plus,
                        shop = R.drawable.ic_giohang,
                        buy = "Mua ngay"
                    ),
                    ThanhPhanUi(
                        image = R.drawable.cookieshazelnutdx,
                        namedrink = "Cookies Hazelnut",
                        price = 43000,
                        minus = R.drawable.ic_minus,
                        increasing = 1,
                        plus = R.drawable.ic_plus,
                        shop = R.drawable.ic_giohang,
                        buy = "Mua ngay"
                    ),
                        ThanhPhanUi(
                            image = R.drawable.cookiesdaumatchadx,
                            namedrink = "Cookies Matcha",
                            price = 40000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.cookiesvietquatcreamcheesedx,
                            namedrink = "Cookies Việt Quất Cream Cheese",
                            price = 45000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        )
                )
            ),//NhómUI cookies đá xay

                NhomUI(
                    imageDrink = R.drawable.attcaffe,
                    nameMon = "Đá xay",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            image = R.drawable.daxaydao,
                            namedrink = "Đá Xay Đào",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.daxaykiwi,
                            namedrink = "Đá xay Kiwi",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.daxaydautay,
                            namedrink = "Đá Xay Dâu Tây",
                            price = 38000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.daxaymatcha,
                            namedrink = "Đá Xay Matcha",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.daxaykhoaimon,
                            namedrink = "Đá Xay Khoai Môn",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.daxaychocolate,
                            namedrink = "Đá Xay Chocolate",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.daxayphucbontu,
                            namedrink = "Đá Xay Phúc Bồn Tử",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        )
                    )
                ),//NhómUI  Đá xay
                NhomUI(
                    imageDrink = R.drawable.attcaffe,
                    nameMon = "Sữa chua",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            image = R.drawable.suachuanho,
                            namedrink = "Sữa chua nho",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.suachuavidao,
                            namedrink = "Sữa Chua Đào",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.suachuachanhday,
                            namedrink = "Sữa Chua Chanh Dây",
                            price = 32000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),

                    )
                ),//NhómUI  Sữa chua
                NhomUI(
                    imageDrink = R.drawable.attcaffe,
                    nameMon = "Soda",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            image = R.drawable.sodadao,
                            namedrink = "Soda Đào",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.sodaxoai,
                            namedrink = "Soda Xoài",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.sodakiwwi,
                            namedrink = "Soda Kiwi",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.sodamamxoi,
                            namedrink = "Soda Mâm xôi",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.sodavietquat,
                            namedrink = "Soda Việt Quốc",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.sodacafechanhbacha,
                            namedrink = "Soda Caffe Chanh Bạc Hà",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),

                        )
                ),//NhómUI  Soda
                NhomUI(
                    imageDrink = R.drawable.attcaffe,
                    nameMon = "Các loại Trà",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            image = R.drawable.traatiso,
                            namedrink = "Trà Atiso",
                            price = 40000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.tracamsa,
                            namedrink = "Trà Cam Xả",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.traolongsen,
                            namedrink = "Trà Olong Sen",
                            price = 38000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.travaihoahong,
                            namedrink = "Trà Vải Hoa Hồng",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            image = R.drawable.tratraicaynhietdoi,
                            namedrink = "Trà Trái Cây Nhiệt Đới",
                            price = 35000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        )
                ),//NhómUI  TRÀ
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(DuLieuDrink) { nhom ->
                    // Hiển thị tên nhóm kèm ảnh nhỏ
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = nhom.imageDrink),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = nhom.nameMon,
                            fontWeight = FontWeight.Bold,
                            fontSize = 27.sp,
                            color = Color.Black
                        )
                    }

                    nhom.danhSachThanhPhan.forEach { thanhPhan ->
                        TheHienThiThanhPhan(thanhPhan,
                            onClick = { /* Xử lý click*/ },
                            onImageClick = {
                                // Khi click vào ảnh, lưu ID ảnh vào biến state để hiển thị Dialog
                                anhDangChon = thanhPhan.image
                            })
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
        // Phần hiển thị Dialog phóng to ảnh
        if (anhDangChon != null) {
            Dialog(
                onDismissRequest = { anhDangChon = null },
                properties = DialogProperties(usePlatformDefaultWidth = false) // Full width
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.9f))
                        .clickable { anhDangChon = null },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = anhDangChon!!),
                        contentDescription = "Ảnh phóng to",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentScale = ContentScale.Fit
                    )


                    IconButton(
                        onClick = { anhDangChon = null },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Đóng",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TheHienThiThanhPhan(duLieu: ThanhPhanUi, onClick: () -> Unit , onImageClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F8)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onImageClick() }
            ) {
                Image(
                    painter = painterResource(id = duLieu.image),
                    contentDescription = duLieu.namedrink,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = duLieu.namedrink,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Text(
                    text = "${duLieu.price}đ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MauCam,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { /* Giảm */ },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(painter = painterResource(id = duLieu.minus), contentDescription = "Trừ", tint = Color.Gray,
                                modifier = Modifier.size(32.dp))
                        }
                        
                        Text(
                            text = "${duLieu.increasing}",
                            modifier = Modifier.padding(horizontal = 8.dp),
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = { /* Tăng */ },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(painter = painterResource(id = duLieu.plus), contentDescription = "Cộng", tint = MauCam,
                                modifier = Modifier.size(32.dp))
                        }
                    }


                    Row {
                        IconButton(onClick = { /* Thêm vào giỏ */ },
                            modifier = Modifier.size(32.dp)) {
                            Icon(painter = painterResource(id = duLieu.shop), contentDescription = "Giỏ hàng", tint = MauCam,
                                modifier = Modifier.size(32.dp))
                        }
                        Button(
                            onClick = { /* Mua ngay */ },
                            colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier
                                .height(38.dp)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                text = duLieu.buy,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTrangChu() {
    MaterialTheme {
        TrangChuContent()
    }
}
