package com.example.wateronl

import android.util.Patterns
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
import androidx.compose.material.icons.filled.Email
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
fun ManHinhQuenMK(
    onQuayLai: () -> Unit,
    onGuiYeuCau: () -> Unit
) {
    var email by remember { mutableStateOf("") }

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
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Nhập địa chỉ Email đã đăng ký của bạn, chúng tôi sẽ gửi mã OTP để đặt lại mật khẩu.",
                        fontSize = 15.sp,
                        color = MauNauDam.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )

                    // Ô NHẬP EMAIL
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Địa chỉ email",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email // Bàn phím có @
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            // 1. Ẩn bàn phím cho thoáng
                            keyboardController?.hide()

                            // 2. Dọn dẹp email
                            val cleanEmail = email.trim()

                            // 3. Logic kiểm tra
                            if (cleanEmail.isEmpty()) {
                                Toast.makeText(context, "Vui lòng nhập địa chỉ email!", Toast.LENGTH_SHORT).show()
                            }
                            else if (!Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
                                Toast.makeText(context, "Email không hợp lệ! (Ví dụ: abc@gmail.com)", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                // 4. Hợp lệ -> Gửi OTP
                                Toast.makeText(context, "Mã OTP đã được gửi đến email của bạn.", Toast.LENGTH_LONG).show()
                                onGuiYeuCau()
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
                            text = "Gửi yêu cầu",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Footer quay về đăng nhập
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