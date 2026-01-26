package com.example.wateronl

import android.view.Gravity
import android.widget.Toast
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

data class ThanhPhanUi(
    val id: String,
    @get:DrawableRes val image: Int,
    val namedrink: String,
    val price: Int,
    @get:DrawableRes val minus: Int,
    val increasing: Int,
    @get:DrawableRes val plus: Int,
    @get:DrawableRes val shop: Int,
    val buy: String,
)

open class NhomUI(

    val nameMon: String,
    val danhSachThanhPhan: List<ThanhPhanUi>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrangChuContent(navController: NavController) {
    var anhDangChon by remember { mutableStateOf<Int?>(null) }
    var selectedCategory by remember { mutableStateOf("Tất cả") }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp),
            colors = CardDefaults.cardColors(
                containerColor = MauCam.copy(alpha = 0.2f)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
            ) {
                val buttonList = listOf(
                    "Tất cả",
                    "Coffee",
                    "Cookies đá xay",
                    "Đá xay",
                    "Sữa chua",
                    "Soda",
                    "Các loại Trà",
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
                        val isSelected = selectedCategory == nameDrink
                        Button(
                            onClick = { selectedCategory = nameDrink },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) MauCam else Color.White,
                                contentColor = if (isSelected) Color.White else Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text(
                                text = nameDrink, fontSize = 13.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            val duLieuDrink = listOf(
                NhomUI(
                    nameMon = "Coffee",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            id = "cfbacsiu",
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
                            id = "cfda",
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
                            id = "cfmuoi",
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
                            id = "cfsua",
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
                ),
                NhomUI(
                    nameMon = "Cookies đá xay",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            id = "cookiesdaudx",
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
                            id = "cookieschocolatepepermintdx",
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
                            id = "cookiesxoaidx",
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
                            id = "cookieshazelnutdx",
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
                            id = "cookiesmatchadx",
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
                            id = "cookiesvietquatcreamcheesedx",
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
                    nameMon = "Đá xay",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            id = "daxaydao",
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
                            id = "daxaykiwi",
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
                            id = "daxaydautay",
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
                            id = "daxaymatcha",
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
                            id = "daxaykhoaimon",
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
                            id = "daxaychocolate",
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
                            id = "daxayphucbontu",
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
                    nameMon = "Sữa chua",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            id = "suachuanho",
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
                            id = "suachuavidao",
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
                            id = "suachuachanhday",
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
                    nameMon = "Soda",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            id = "sodadao",
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
                            id = "sodaxoai",
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
                            id = "sodakiwi",
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
                            id = "sodamamxoi",
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
                            id = "sodavietquat",
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
                            id = "sodacafechanhbacha",
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
                    nameMon = "Các loại Trà",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            id = "traatiso",
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
                            id = "tracamsa",
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
                            id = "traolongsen",
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
                            id = "travaihoahong",
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
                            id = "tratraicaynhietdoi",
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
                NhomUI(
                    nameMon = "Nước ép",
                    danhSachThanhPhan = listOf(
                        ThanhPhanUi(
                            id = "nethom",
                            image = R.drawable.nethom,
                            namedrink = "Nước ép thơm",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            id = "nebuoi",
                            image = R.drawable.nebuoi,
                            namedrink = "Nước ép bưởi",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            id = "netao",
                            image = R.drawable.netao,
                            namedrink = "Nước ép táo",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            id = "necam",
                            image = R.drawable.necam,
                            namedrink = "Nước ép cam",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                        ThanhPhanUi(
                            id = "neduahau",
                            image = R.drawable.neduahau,
                            namedrink = "Nước ép dưa hấu",
                            price = 30000,
                            minus = R.drawable.ic_minus,
                            increasing = 1,
                            plus = R.drawable.ic_plus,
                            shop = R.drawable.ic_giohang,
                            buy = "Mua ngay"
                        ),
                    )
                )
            )

            val filteredDuLieuDrink = if (selectedCategory == "Tất cả") {
                duLieuDrink
            } else {
                duLieuDrink.filter { it.nameMon == selectedCategory }
            }

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                filteredDuLieuDrink.forEach { nhom ->
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = nhom.nameMon,
                                fontWeight = FontWeight.Bold,
                                fontSize = 27.sp,
                                color = MauCam
                            )
                        }
                    }
                    items(nhom.danhSachThanhPhan) { thanhPhan ->
                        TheHienThiThanhPhan(
                            thanhPhan,
                            onClick = { /* Xử lý click*/ },
                            onImageClick = {
                                anhDangChon = thanhPhan.image
                            },
                            navController = navController
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
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
                    AsyncImage(
                        model = anhDangChon!!,
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
fun TheHienThiThanhPhan(
    duLieu: ThanhPhanUi,
    onClick: () -> Unit,
    onImageClick: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    var soLuong by remember { mutableIntStateOf(duLieu.increasing) }
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
                AsyncImage(
                    model = duLieu.image,
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
                            onClick = { if (soLuong > 1) soLuong-- },
                            modifier = Modifier.size(25.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = duLieu.minus),
                                contentDescription = "Trừ",
                                tint = Color.Gray,
                                modifier = Modifier.size(25.dp)
                            )
                        }

                        Text(
                            text = "$soLuong",
                            modifier = Modifier.padding(horizontal = 8.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = { soLuong++ },
                            modifier = Modifier.size(25.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = duLieu.plus),
                                contentDescription = "Cộng",
                                tint = MauCam,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                val sanPhamVoiSoLuong = duLieu.copy(increasing = soLuong)
                                GioHangData.themVaoGio(sanPhamVoiSoLuong)
                                val toast = Toast.makeText(
                                    context,
                                    "Đã thêm $soLuong ${duLieu.namedrink} vào giỏ!",
                                    Toast.LENGTH_SHORT
                                )
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                            },
                            modifier = Modifier
                                .size(47.dp)
                                .padding(start = 20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = duLieu.shop),
                                contentDescription = "Giỏ hàng",
                                tint = MauCam,
                                modifier = Modifier.size(47.dp)
                            )
                        }


                        Row {

                        }
                        Button(
                            onClick = {
                                val sanPhamVoiSoLuong = duLieu.copy(increasing = soLuong)
                                ThanhToanData.setDanhSachThanhToan(listOf(sanPhamVoiSoLuong))
                                navController.navigate("thanh_toan")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                            modifier = Modifier
                                .height(30.dp)
                                .padding(start = 17.dp)
                        ) {
                            Text(
                                text = duLieu.buy,
                                fontSize = 10.sp,
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTrangChu() {
    MaterialTheme {
        TrangChuContent(navController = rememberNavController())
    }
}
