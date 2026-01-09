package com.example.wateronl

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// --- PHAN 1: LOGIC ---
@Composable
fun ManHinhCaNhan(
    onDangXuat: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val ten by viewModel.hoTen.collectAsState()
    val email by viewModel.email.collectAsState()
    val laGoogle by viewModel.laTaiKhoanGoogle.collectAsState()

    GiaoDienCaNhan(
        ten = ten,
        email = email,
        hienNutDoiMatKhau = !laGoogle,
        onDangXuat = {
            viewModel.dangXuat()
            onDangXuat()
        },
        onDoiTen = { tenMoi -> viewModel.capNhatHoTen(tenMoi) },
        onDoiMatKhau = { mkMoi ->
            // Goi ViewModel xu ly doi pass
            viewModel.doiMatKhau(
                matKhauMoi = mkMoi,
                onThanhCong = {
                    Toast.makeText(context, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show()
                },
                onThatBai = { loi ->
                    // Firebase yeu cau neu lau qua chua dang nhap lai thi khong cho doi pass
                    Toast.makeText(context, "Lỗi: $loi. Hãy đăng nhập lại rồi thử nhé!", Toast.LENGTH_LONG).show()
                }
            )
        }
    )
}

// --- PHAN 2: GIAO DIEN ---
@Composable
fun GiaoDienCaNhan(
    ten: String,
    email: String,
    hienNutDoiMatKhau: Boolean,
    onDangXuat: () -> Unit,
    onDoiTen: (String) -> Unit,
    onDoiMatKhau: (String) -> Unit
) {
    // Bien quan ly hop thoai doi TEN
    var hienDialogDoiTen by remember { mutableStateOf(false) }
    var tenMoiNhap by remember { mutableStateOf("") }

    // Bien quan ly hop thoai doi MAT KHAU
    var hienDialogDoiMK by remember { mutableStateOf(false) }
    var matKhauMoi by remember { mutableStateOf("") }
    var hienThiMatKhau by remember { mutableStateOf(false) }

    // --- DIALOG DOI TEN ---
    if (hienDialogDoiTen) {
        AlertDialog(
            onDismissRequest = { hienDialogDoiTen = false },
            title = { Text(text = "Cập nhật tên", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Nhập tên hiển thị mới:")
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(value = tenMoiNhap, onValueChange = { tenMoiNhap = it }, singleLine = true, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (tenMoiNhap.isNotEmpty()) {
                        onDoiTen(tenMoiNhap)
                        hienDialogDoiTen = false
                    }
                }) { Text("Lưu", color = Color(0xFFD4A373)) }
            },
            dismissButton = { TextButton(onClick = { hienDialogDoiTen = false }) { Text("Hủy", color = Color.Gray) } },
            containerColor = Color.White
        )
    }

    // --- DIALOG DOI MAT KHAU ---
    if (hienDialogDoiMK) {
        AlertDialog(
            onDismissRequest = { hienDialogDoiMK = false },
            title = { Text(text = "Đổi mật khẩu", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Nhập mật khẩu mới:")
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = matKhauMoi,
                        onValueChange = { matKhauMoi = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (hienThiMatKhau) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { hienThiMatKhau = !hienThiMatKhau }) {
                                Icon(
                                    imageVector = if (hienThiMatKhau) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (matKhauMoi.length >= 6) {
                        onDoiMatKhau(matKhauMoi)
                        hienDialogDoiMK = false
                        matKhauMoi = "" // Xoa trang sau khi doi
                    }
                }) { Text("Lưu", color = Color(0xFFD4A373)) }
            },
            dismissButton = { TextButton(onClick = { hienDialogDoiMK = false }) { Text("Hủy", color = Color.Gray) } },
            containerColor = Color.White
        )
    }

    // --- GIAO DIEN CHINH ---
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9F9F9)).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Thông tin cá nhân", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4A3B32), modifier = Modifier.padding(top = 16.dp, bottom = 32.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(70.dp), shape = CircleShape, color = Color.LightGray) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(10.dp), tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = ten, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4A3B32))
                Text(text = email, fontSize = 14.sp, color = Color.Gray)
            }
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray, modifier = Modifier.size(24.dp).clickable {
                tenMoiNhap = ten
                hienDialogDoiTen = true
            })
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Cac nut chuc nang
        MucChonProfile(icon = Icons.Default.Person, tieuDe = "Thông tin tài khoản") { }

        // Nut DOI MAT KHAU da duoc gan su kien
        if (hienNutDoiMatKhau) {
            MucChonProfile(icon = Icons.Default.Lock, tieuDe = "Thay mật khẩu") {
                hienDialogDoiMK = true
            }
        }

        MucChonProfile(icon = Icons.Default.Settings, tieuDe = "Cài đặt") { }
        MucChonProfile(icon = Icons.Default.ExitToApp, tieuDe = "Đăng xuất", isRed = true, onClick = onDangXuat)
    }
}

// Component con
@Composable
fun MucChonProfile(icon: ImageVector, tieuDe: String, isRed: Boolean = false, onClick: () -> Unit) {
    val mauChu = if (isRed) Color.Red else Color(0xFF4A3B32)
    Column(modifier = Modifier.clickable { onClick() }) {
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = if (isRed) Color.Red else Color.Gray, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = tieuDe, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = mauChu, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
    }
}

// --- PHAN 3: PREVIEW ---
@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    GiaoDienCaNhan(
        ten = "Nguyễn Văn A",
        email = "test@gmail.com",
        hienNutDoiMatKhau = true,
        onDangXuat = {},
        onDoiTen = {},
        onDoiMatKhau = {}
    )
}