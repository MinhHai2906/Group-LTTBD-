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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ManHinhDangNhap(
    onChuyenSangDangKy: () -> Unit,
    onDangNhapThanhCong: () -> Unit,
    onQuenMatKhau: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var matKhau by remember { mutableStateOf("") }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNenKem)
    ) {
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
                    modifier = Modifier.size(120.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MauCam.copy(alpha = 0.2f),
                        modifier = Modifier.fillMaxSize()
                    ) {}
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo Coffee",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Chào mừng trở lại!"
                    , fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauNauDam)
                Text(text = "Bùng nổ vị giác cùng chúng tôi",
                    fontSize = 14.sp,
                    color = MauNauDam.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 8.dp))
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
                    modifier = Modifier
                        .padding(32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // TAB
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)) {
                        Column(modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Đăng nhập",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MauNauDam
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier
                                .width(80.dp)
                                .height(3.dp)
                                .background(MauCam))
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onChuyenSangDangKy() },
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Đăng ký",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray)
                        }
                    }

                    // INPUTS
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Địa chỉ email",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email
                    )
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = matKhau,
                        onValueChange = { matKhau = it },
                        placeholder = "Mật khẩu",
                        icon = Icons.Default.Lock,
                        isPassword = true
                    )

                    // Quên mật khẩu
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Quên mật khẩu?",
                            modifier = Modifier.clickable { onQuenMatKhau() },
                            textAlign = TextAlign.End,
                            color = MauNauDam.copy(alpha = 0.6f),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // BUTTON ĐĂNG NHẬP
                    Button(
                        onClick = {
                            keyboardController?.hide() // Ẩn bàn phím

                            val cleanEmail = email.trim()

                            // LOGIC KIỂM TRA ĐỒNG BỘ VỚI ĐĂNG KÝ
                            if (cleanEmail.isEmpty() || matKhau.isEmpty()) {
                                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                            }
                            else if (!Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
                                Toast.makeText(context, "Email không hợp lệ!", Toast.LENGTH_SHORT).show()
                            }
                            else if (matKhau.contains(" ")) {
                                Toast.makeText(context, "Mật khẩu không được chứa khoảng trắng!", Toast.LENGTH_SHORT).show()
                            }
                            // Thêm check độ dài
                            else if (matKhau.length < 6) {
                                Toast.makeText(context, "Mật khẩu quá ngắn (phải từ 6 ký tự)!", Toast.LENGTH_SHORT).show()
                            }
                            // Thêm check ký tự đầu (Chặn @Quang)
                            else if (!matKhau[0].isLetterOrDigit()) {
                                Toast.makeText(context, "Mật khẩu phải bắt đầu bằng chữ hoặc số!", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                                onDangNhapThanhCong()
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
                            text = "Đăng nhập",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Footer
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Chưa có tài khoản? ",
                            color = MauNauDam.copy(alpha = 0.6f)
                        )
                        Text(
                            text = "Đăng ký",
                            modifier = Modifier.clickable { onChuyenSangDangKy() },
                            style = TextStyle(
                                color = MauCam,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDangNhap() {
    ManHinhDangNhap(
        onChuyenSangDangKy = {},
        onDangNhapThanhCong = {},
        onQuenMatKhau = {}
    )
}