package com.example.wateronl

import android.widget.Toast
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val MauNenKem = Color(0xFFFDF6E3)
val MauTrangCard = Color(0xFFFFFFFF)
val MauNauDam = Color(0xFF4A3B32)
val MauCam = Color(0xFFD4A373)
val MauNenInput = Color(0xFFFEFAE0)

@Composable
fun ManHinhDangKy(onQuayLaiDangNhap: () -> Unit) {
    var tenNguoiDung by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var matKhau by remember { mutableStateOf("") }
    var xacNhanMatKhau by remember { mutableStateOf("") }

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
                        modifier = Modifier.size(100.dp).clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Xin chào!"
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
                        Column(modifier = Modifier.weight(1f)
                            .clickable { onQuayLaiDangNhap() },
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Đăng nhập",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                        Column(modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Đăng ký",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MauNauDam)
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier
                                .width(80.dp)
                                .height(3.dp)
                                .background(MauCam))
                        }
                    }

                    // INPUTS

                    // 1. Tên người dùng:
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = tenNguoiDung,
                        onValueChange = { tenNguoiDung = it },
                        placeholder = "Tên người dùng",
                        icon = Icons.Default.Person,
                        capitalization = KeyboardCapitalization.None
                    )

                    // 2. Email: Bàn phím Email
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Địa chỉ email",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email
                    )

                    // 3. Mật khẩu
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = matKhau,
                        onValueChange = { matKhau = it },
                        placeholder = "Mật khẩu",
                        icon = Icons.Default.Lock,
                        isPassword = true
                    )

                    // 4. Xác nhận mật khẩu
                    O_Nhap_Lieu_Tuy_Chinh(
                        value = xacNhanMatKhau,
                        onValueChange = { xacNhanMatKhau = it },
                        placeholder = "Xác nhận mật khẩu",
                        icon = Icons.Default.Lock,
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // BUTTON
                    Button(
                        onClick = {
                            keyboardController?.hide()

                            val cleanEmail = email.trim()

                            // LOGIC KIỂM TRA
                            if (tenNguoiDung.isEmpty() || cleanEmail.isEmpty() || matKhau.isEmpty()) {
                                Toast.makeText(context, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                            }
                            // Tên người dùng:

                            // Email: Bắt buộc đúng chuẩn
                            else if (!Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
                                Toast.makeText(context, "Email không hợp lệ!", Toast.LENGTH_SHORT).show()
                            }
                            // Mật khẩu: Cấm tiệt dấu cách
                            else if (matKhau.contains(" ")) {
                                Toast.makeText(context, "Mật khẩu không được chứa khoảng trắng!", Toast.LENGTH_SHORT).show()
                            }
                            // Mật khẩu: Độ dài >= 6
                            else if (matKhau.length < 6) {
                                Toast.makeText(context, "Mật khẩu phải từ 6 ký tự trở lên!", Toast.LENGTH_SHORT).show()
                            }
                            // Mật khẩu: Ký tự đầu phải là chữ/số
                            else if (matKhau.isNotEmpty() && !matKhau[0].isLetterOrDigit()) {
                                Toast.makeText(context, "Mật khẩu phải bắt đầu bằng chữ hoặc số!", Toast.LENGTH_SHORT).show()
                            }
                            // Xác nhận: Phải giống y hệt
                            else if (matKhau != xacNhanMatKhau) {
                                Toast.makeText(context, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(context, "Đăng ký thành công! Hãy đăng nhập ngay.", Toast.LENGTH_LONG).show()
                                onQuayLaiDangNhap()
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
                            text = "Đăng ký",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

// Component nhập liệu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun O_Nhap_Lieu_Tuy_Chinh(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None // Mặc định là không can thiệp
) {
    var hienMatKhau by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = MauNauDam.copy(alpha = 0.4f)) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = null, tint = MauCam) },

        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else keyboardType,
            capitalization = capitalization,
            imeAction = ImeAction.Next
        ),

        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { hienMatKhau = !hienMatKhau }) {
                    Icon(
                        imageVector = if (hienMatKhau) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        tint = MauNauDam.copy(alpha = 0.4f)
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !hienMatKhau) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MauNenInput,
            unfocusedContainerColor = MauNenInput,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MauCam
        ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth().height(56.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDangKy() {
    ManHinhDangKy(onQuayLaiDangNhap = {})
}