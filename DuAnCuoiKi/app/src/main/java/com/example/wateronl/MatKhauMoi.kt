package com.example.wateronl

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ManHinhMatKhauMoi(
    onQuayLai: () -> Unit,
    onDoiMatKhauThanhCong: () -> Unit
) {
    var matKhauMoi by remember { mutableStateOf("") }
    var xacNhanMatKhau by remember { mutableStateOf("") }

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
                    text = "Tạo mật khẩu mới",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauNauDam
                )
                Text(
                    text = "Hãy nhập mật khẩu mạnh để bảo vệ tài khoản",
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

                    // Ô nhập mật khẩu mới
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = matKhauMoi,
                        onValueChange = { matKhauMoi = it },
                        placeholder = "Mật khẩu mới",
                        icon = Icons.Default.Lock,
                        isPassword = true
                    )

                    // Ô nhập lại mật khẩu
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = xacNhanMatKhau,
                        onValueChange = { xacNhanMatKhau = it },
                        placeholder = "Xác nhận mật khẩu",
                        icon = Icons.Default.Lock,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()

                            // Kiểm tra rỗng
                            if (matKhauMoi.isEmpty() || xacNhanMatKhau.isEmpty()) {
                                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                            }
                            // Kiểm tra khoảng trắng
                            else if (matKhauMoi.contains(" ")) {
                                Toast.makeText(context, "Mật khẩu không được chứa khoảng trắng!", Toast.LENGTH_SHORT).show()
                            }
                            // Kiểm tra độ dài
                            else if (matKhauMoi.length < 6) {
                                Toast.makeText(context, "Mật khẩu phải từ 6 ký tự trở lên!", Toast.LENGTH_SHORT).show()
                            }
                            // Kiểm tra ký tự đầu
                            else if (!matKhauMoi[0].isLetterOrDigit()) {
                                Toast.makeText(context, "Mật khẩu phải bắt đầu bằng chữ hoặc số!", Toast.LENGTH_SHORT).show()
                            }
                            // Kiểm tra khớp
                            else if (matKhauMoi != xacNhanMatKhau) {
                                Toast.makeText(context, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                // Thành công
                                Toast.makeText(context, "Đổi mật khẩu thành công! Hãy đăng nhập lại.", Toast.LENGTH_LONG).show()
                                onDoiMatKhauThanhCong()
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
                            text = "Đổi mật khẩu",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMatKhauMoi() {
    ManHinhMatKhauMoi(onQuayLai = {}, onDoiMatKhauThanhCong = {})
}