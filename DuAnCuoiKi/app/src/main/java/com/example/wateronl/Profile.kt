package com.example.wateronl

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ManHinhCaNhan(
    onDangXuat: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val ten by viewModel.hoTen.collectAsState()
    val email by viewModel.email.collectAsState()
    val sdt by viewModel.sdt.collectAsState()
    val diaChi by viewModel.diaChi.collectAsState()
    val gioiTinh by viewModel.gioiTinh.collectAsState()
    val ngaySinh by viewModel.ngaySinh.collectAsState()
    val avatarCode by viewModel.avatarCode.collectAsState()
    val laGoogle by viewModel.laTaiKhoanGoogle.collectAsState()
    val nhanThongBao by viewModel.nhanThongBao.collectAsState()

    GiaoDienCaNhan(
        ten = ten,
        email = email,
        sdt = sdt,
        diaChi = diaChi,
        gioiTinh = gioiTinh,
        ngaySinh = ngaySinh,
        avatarCode = avatarCode,
        nhanThongBao = nhanThongBao,
        hienNutDoiMatKhau = !laGoogle,
        onDangXuat = {
            viewModel.dangXuat()
            onDangXuat()
        },
        onDoiTen = { tenMoi -> viewModel.capNhatHoTen(tenMoi) },
        onDoiMatKhau = { mkMoi ->
            viewModel.doiMatKhau(mkMoi, { Toast.makeText(context, "Thành công!", Toast.LENGTH_SHORT).show() }, { Toast.makeText(context, "Lỗi: $it", Toast.LENGTH_SHORT).show() })
        },
        onLuuThongTin = { s, d, g, n -> // Them tham so
            viewModel.capNhatThongTinChiTiet(s, d, g, n)
            Toast.makeText(context, "Đã lưu hồ sơ!", Toast.LENGTH_SHORT).show()
        },
        onDoiAvatar = { maMoi -> viewModel.doiAvatar(maMoi) },
        // Callback lưu cài đặt
        onLuuCaiDat = { trangThaiMoi -> viewModel.capNhatCaiDat(trangThaiMoi) }
    )
}

@Composable
fun GiaoDienCaNhan(
    ten: String, email: String, sdt: String, diaChi: String,
    gioiTinh: String, ngaySinh: String,
    avatarCode: String,
    nhanThongBao: Boolean,
    hienNutDoiMatKhau: Boolean,
    onDangXuat: () -> Unit, onDoiTen: (String) -> Unit, onDoiMatKhau: (String) -> Unit,
    onLuuThongTin: (String, String, String, String) -> Unit,
    onDoiAvatar: (String) -> Unit,
    onLuuCaiDat: (Boolean) -> Unit
) {
    // Cac bien Dialog
    var hienDialogDoiTen by remember { mutableStateOf(false) }
    var tenMoiNhap by remember { mutableStateOf("") }
    var hienDialogDoiMK by remember { mutableStateOf(false) }
    var matKhauMoi by remember { mutableStateOf("") }
    var hienThiMatKhau by remember { mutableStateOf(false) }
    var hienDialogAvatar by remember { mutableStateOf(false) }
    var hienDialogXacNhanDangXuat by remember { mutableStateOf(false) }

    // biến dialog cài đặt
    var hienDialogCaiDat by remember { mutableStateOf(false) }

    // Dialog thông tin
    var hienDialogThongTin by remember { mutableStateOf(false) }
    var sdtNhap by remember { mutableStateOf("") }
    var diaChiNhap by remember { mutableStateOf("") }
    var gioiTinhNhap by remember { mutableStateOf("Nam") }
    var ngaySinhNhap by remember { mutableStateOf("") }

    // dialog đổi tên
    if (hienDialogDoiTen) {
        AlertDialog(
            onDismissRequest = { hienDialogDoiTen = false },
            title = { Text("Tên mới", fontWeight = FontWeight.Bold) },
            text = { OutlinedTextField(tenMoiNhap, { tenMoiNhap = it }, label = { Text("Tên hiển thị") }) },
            confirmButton = { TextButton({ if(tenMoiNhap.isNotEmpty()){ onDoiTen(tenMoiNhap); hienDialogDoiTen = false } }) { Text("Lưu", color = MauCam) } },
            dismissButton = { TextButton({ hienDialogDoiTen = false }) { Text("Hủy") } }, containerColor = Color.White
        )
    }

    // dialog đổi mk
    if (hienDialogDoiMK) {
        AlertDialog(
            onDismissRequest = { hienDialogDoiMK = false },
            title = { Text("Đổi mật khẩu", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    matKhauMoi, { matKhauMoi = it }, label = { Text("Mật khẩu mới") },
                    visualTransformation = if (hienThiMatKhau) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = { IconButton({ hienThiMatKhau = !hienThiMatKhau }) { Icon(if (hienThiMatKhau) Icons.Default.VisibilityOff else Icons.Default.Visibility, null) } }
                )
            },
            confirmButton = { TextButton({ if(matKhauMoi.length >= 6){ onDoiMatKhau(matKhauMoi); hienDialogDoiMK = false; matKhauMoi="" } }) { Text("Lưu", color = MauCam) } },
            dismissButton = { TextButton({ hienDialogDoiMK = false }) { Text("Hủy") } }, containerColor = Color.White
        )
    }

    // dialog thông tin cá nhân
    if (hienDialogThongTin) {
        AlertDialog(
            onDismissRequest = { hienDialogThongTin = false },
            title = { Text("Hồ sơ cá nhân", fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    OutlinedTextField(sdtNhap, { sdtNhap = it }, label = { Text("Số điện thoại") }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(diaChiNhap, { diaChiNhap = it }, label = { Text("Địa chỉ") }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(ngaySinhNhap, { ngaySinhNhap = it }, label = { Text("Ngày sinh (dd/mm/yyyy)") }, modifier = Modifier.fillMaxWidth())

                    Spacer(Modifier.height(12.dp))
                    Text("Giới tính:", fontWeight = FontWeight.Medium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = gioiTinhNhap == "Nam", onClick = { gioiTinhNhap = "Nam" }, colors = RadioButtonDefaults.colors(selectedColor = MauCam))
                        Text("Nam")
                        Spacer(Modifier.width(16.dp))
                        RadioButton(selected = gioiTinhNhap == "Nữ", onClick = { gioiTinhNhap = "Nữ" }, colors = RadioButtonDefaults.colors(selectedColor = MauCam))
                        Text("Nữ")
                    }
                }
            },
            confirmButton = { TextButton({ onLuuThongTin(sdtNhap, diaChiNhap, gioiTinhNhap, ngaySinhNhap); hienDialogThongTin = false }) { Text("Lưu", color = MauCam) } },
            dismissButton = { TextButton({ hienDialogThongTin = false }) { Text("Hủy") } }, containerColor = Color.White
        )
    }

    // dialog chọn avt
    if (hienDialogAvatar) {
        AlertDialog(
            onDismissRequest = { hienDialogAvatar = false },
            title = { Text("Chọn Avatar", fontWeight = FontWeight.Bold) },
            text = {
                LazyVerticalGrid(columns = GridCells.Fixed(3), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(AvatarList.danhSach.toList()) { (ma, idAnh) ->
                        Image(painter = painterResource(id = idAnh), contentDescription = null, modifier = Modifier.size(80.dp).clip(CircleShape).border(2.dp, if (avatarCode == ma) MauCam else Color.Transparent, CircleShape).clickable { onDoiAvatar(ma); hienDialogAvatar = false }, contentScale = ContentScale.Crop)
                    }
                }
            },
            confirmButton = {}, dismissButton = { TextButton({ hienDialogAvatar = false }) { Text("Đóng") } }, containerColor = Color.White
        )
    }

    // dialog xác nhân đăng xuất
    if (hienDialogXacNhanDangXuat) {
        AlertDialog(
            onDismissRequest = { hienDialogXacNhanDangXuat = false },
            title = { Text("Đăng xuất") },
            text = { Text("Bạn có chắc chắn muốn đăng xuất không?") },
            confirmButton = { TextButton(onClick = { hienDialogXacNhanDangXuat = false; onDangXuat() }) { Text("Đồng ý", color = Color.Red) } },
            dismissButton = { TextButton(onClick = { hienDialogXacNhanDangXuat = false }) { Text("Hủy") } },
            containerColor = Color.White
        )
    }

    // dialog cài đặt
    if (hienDialogCaiDat) {
        AlertDialog(
            onDismissRequest = { hienDialogCaiDat = false },
            title = { Text("Cài đặt ứng dụng", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    // Thông báo
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Nhận thông báo", fontWeight = FontWeight.Medium)
                            Text("Nhận tin khuyến mãi mới nhất", fontSize = 12.sp, color = Color.Gray)
                        }
                        Switch(
                            checked = nhanThongBao,
                            onCheckedChange = { onLuuCaiDat(it) }, // Lưu ngay khi bấm
                            colors = SwitchDefaults.colors(checkedThumbColor = MauCam, checkedTrackColor = MauCam.copy(alpha = 0.2f))
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))

                    // Ngôn ngữ
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Ngôn ngữ", fontWeight = FontWeight.Medium)
                        Text("Tiếng Việt", color = Color.Gray, fontSize = 14.sp)
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))

                    // Phiên bản
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Phiên bản", fontWeight = FontWeight.Medium)
                        Text("1.0.0 (Beta)", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { hienDialogCaiDat = false }) { Text("Đóng", color = MauCam) }
            },
            containerColor = Color.White
        )
    }

    // giao diện chính
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9F9F9)).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Thông tin cá nhân", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MauNauDam, modifier = Modifier.padding(top = 16.dp, bottom = 32.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(painter = painterResource(id = AvatarList.layAnhTuMa(avatarCode)), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(80.dp).clip(CircleShape).border(2.dp, Color.LightGray, CircleShape).clickable { hienDialogAvatar = true })
                Icon(Icons.Default.CameraAlt, null, modifier = Modifier.size(24.dp).background(Color.White, CircleShape).padding(4.dp), tint = MauCam)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(ten, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MauNauDam)
                Text(email, fontSize = 14.sp, color = Color.Gray)
                // Hien thi tong quan thong tin
                if (sdt.isNotEmpty()) Text("SĐT: $sdt", fontSize = 13.sp, color = MauCam)
                if (gioiTinh.isNotEmpty()) Text("GT: $gioiTinh", fontSize = 13.sp, color = Color.Gray)
                // --- ĐÃ THÊM DÒNG HIỂN THỊ NGÀY SINH ---
                if (ngaySinh.isNotEmpty()) Text("NS: $ngaySinh", fontSize = 13.sp, color = Color.Gray)
            }
            Icon(Icons.Default.Edit, null, tint = Color.Gray, modifier = Modifier.size(24.dp).clickable { tenMoiNhap = ten; hienDialogDoiTen = true })
        }

        Spacer(modifier = Modifier.height(40.dp))
        MucChonProfile(Icons.Default.Person, "Hồ sơ cá nhân") {
            sdtNhap = sdt
            diaChiNhap = diaChi
            gioiTinhNhap = gioiTinh
            ngaySinhNhap = ngaySinh
            hienDialogThongTin = true
        }

        if (hienNutDoiMatKhau) MucChonProfile(Icons.Default.Lock, "Thay mật khẩu") { hienDialogDoiMK = true }

        // kích hoạt dialog cài đặt
        MucChonProfile(Icons.Default.Settings, "Cài đặt") { hienDialogCaiDat = true }

        // Bam vao day se hien dialog xac nhan truoc khi out
        MucChonProfile(Icons.Default.ExitToApp, "Đăng xuất", true) { hienDialogXacNhanDangXuat = true }
    }
}

@Composable
fun MucChonProfile(icon: ImageVector, tieuDe: String, isRed: Boolean = false, onClick: () -> Unit) {
    val mauChu = if (isRed) Color.Red else MauNauDam
    Column(modifier = Modifier.clickable { onClick() }) {
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = if (isRed) Color.Red else Color.Gray, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(tieuDe, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = mauChu, modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.LightGray)
        }
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    GiaoDienCaNhan("Test Name", "test@gmail.com", "0909", "HCM", "Nam", "01/01/2000", "avatar_1", true, true, {}, {}, {}, {_,_,_,_->}, {}, {})
}