package com.example.wateronl

import android.widget.Toast
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ManHinhXacThucOTP(
    onQuayLai: () -> Unit,
    onXacThucThanhCong: () -> Unit
) {
    var maOTP by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNenKem)
    ) {
        // Nút Back
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
            // HEADER
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
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Nhập mã xác thực",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauNauDam
                )
                Text(
                    text = "Mã xác thực đã được gửi đến email của bạn.\nVui lòng nhập mã để tiếp tục.",
                    fontSize = 14.sp,
                    color = MauNauDam.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, start = 32.dp, end = 32.dp)
                )
            }

            // BODY
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
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Ô NHẬP OTP
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = maOTP,
                        onValueChange = {
                            // Chỉ cho phép nhập số
                            if (it.all { char -> char.isDigit() }) {
                                maOTP = it
                            }
                        },
                        placeholder = "Nhập mã xác thực (6 số)",
                        icon = Icons.Default.VpnKey,
                        keyboardType = KeyboardType.Number // Bàn phím số
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // BUTTON XÁC NHẬN
                    Button(
                        onClick = {
                            keyboardController?.hide()

                            if (maOTP.isEmpty()) {
                                Toast.makeText(context, "Vui lòng nhập mã OTP!", Toast.LENGTH_SHORT).show()
                            } else if (maOTP.length < 6) { // Giả sử mã OTP chuẩn là 6 số
                                Toast.makeText(context, "Mã OTP phải đủ 6 chữ số!", Toast.LENGTH_SHORT).show()
                            } else {
                                // Thành công
                                onXacThucThanhCong()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(10.dp, RoundedCornerShape(16.dp), spotColor = MauCam),
                        colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Xác nhận mã",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    // Gửi lại mã
                    Text(
                        text = "Gửi lại mã",
                        color = MauCam,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable {
                                Toast.makeText(context, "Đã gửi lại mã mới!", Toast.LENGTH_SHORT).show()
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOTP() {
    ManHinhXacThucOTP(onQuayLai = {}, onXacThucThanhCong = {})
}