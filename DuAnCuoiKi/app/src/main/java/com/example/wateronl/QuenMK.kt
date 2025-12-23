package com.example.wateronl

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ManHinhQuenMK(
    onQuayLai: () -> Unit,
    onGuiYeuCau: () -> Unit
) {
    var email by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNenKem)
    ) {
        // Nút Back nhỏ ở góc trên bên trái
        IconButton(
            onClick = { onQuayLai() },
            modifier = Modifier
                .padding(top = 48.dp, start = 16.dp)
                .align(Alignment.TopStart)
                .zIndex(1f)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Quay lại",
                tint = MauNauDam
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Phần tiêu đề & Logo
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(100.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MauCam.copy(alpha = 0.2f),
                        modifier = Modifier.fillMaxSize()
                    ) {}
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(80.dp).clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Quên Mật Khẩu?",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauNauDam
                )
                Text(
                    text = "Đừng lo, hãy nhập email để khôi phục",
                    fontSize = 14.sp,
                    color = MauNauDam.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Card trắng chứa form
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MauTrangCard,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Nhập địa chỉ Email đã đăng ký của bạn, chúng tôi sẽ gửi mã OTP để đặt lại mật khẩu.",
                        fontSize = 15.sp,
                        color = MauNauDam.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )

                    O_Nhap_Lieu_Tuy_Chinh(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Địa chỉ email",
                        icon = Icons.Default.Email
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { onGuiYeuCau() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(10.dp, RoundedCornerShape(16.dp), spotColor = MauCam),
                        colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Gửi yêu cầu",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Nút quay lại đăng nhập text
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Nhớ mật khẩu rồi? ",
                            color = MauNauDam.copy(alpha = 0.6f)
                        )
                        Text(
                            text = "Đăng nhập ngay",
                            modifier = Modifier.clickable { onQuayLai() },
                            fontWeight = FontWeight.Bold,
                            color = MauCam
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewQuenMK() {
    ManHinhQuenMK(onQuayLai = {}, onGuiYeuCau = {})
}