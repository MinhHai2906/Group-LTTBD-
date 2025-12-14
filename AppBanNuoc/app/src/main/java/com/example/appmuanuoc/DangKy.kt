package com.example.appmuanuoc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
val MauNenKem = Color(0xFFFDF6E3)
val MauTrangCard = Color(0xFFFFFFFF)
val MauNauDam = Color(0xFF4A3B32)
val MauCam = Color(0xFFD4A373)
@Composable
fun ManHinhDangKy() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNenKem) // to nen thanh mau kem
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally // can giua
        ) {
            Column(
                modifier = Modifier .weight(0.3f) .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MauCam.copy(alpha = 0.2f), //do nhat 20%
                        modifier = Modifier.fillMaxSize()
                    ) { }
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo Coffee",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp).clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Xin chào!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauNauDam
                )

                Text(
                    text = "Thưởng thức đồ uống cùng chúng tôi",
                    fontSize = 14.sp,
                    color = MauNauDam.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 8.dp)
                )
        }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f), // 70% MAN HINH
                color = MauTrangCard,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f), // 50% chieu ngang
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text ="Đăng nhập",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray //Xam nhat
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        //Tab dang ky
                        Column(
                            modifier = Modifier.weight(1f), // 50% chieu ngang
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Đăng ký",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MauNauDam //Nau dam
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // Gach duoi neu dang chon
                            Box(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(3.dp)
                                    .background(MauCam)
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
fun PreviewDangKy() {
    ManHinhDangKy()
}