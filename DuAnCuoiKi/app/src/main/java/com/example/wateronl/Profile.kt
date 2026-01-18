package com.example.wateronl

import android.widget.Toast
import androidx.compose.animation.core.*
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
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
    val hangThanhVien by viewModel.hangThanhVien.collectAsState()
    val tongTien by viewModel.tongTienTichLuy.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()
    val mucDoHoanThien by viewModel.mucDoHoanThien.collectAsState()

    GiaoDienCaNhan(
        ten = ten,
        email = email,
        sdt = sdt,
        diaChi = diaChi,
        gioiTinh = gioiTinh,
        ngaySinh = ngaySinh,
        avatarCode = avatarCode,
        hangThanhVien = hangThanhVien,
        tongTien = tongTien,
        nhanThongBao = nhanThongBao,
        hienNutDoiMatKhau = !laGoogle,
        isLoading = isLoading,
        mucDoHoanThien = mucDoHoanThien,
        onDangXuat = { viewModel.dangXuat(); onDangXuat() },
        onDoiTen = { viewModel.capNhatHoTen(it) },
        onDoiMatKhau = { mk -> viewModel.doiMatKhau(mk, { Toast.makeText(context, "Thành công!", Toast.LENGTH_SHORT).show() }, { Toast.makeText(context, "Lỗi: $it", Toast.LENGTH_SHORT).show() }) },
        onLuuThongTin = { s, d, g, n -> viewModel.capNhatThongTinChiTiet(s, d, g, n); Toast.makeText(context, "Đã lưu!", Toast.LENGTH_SHORT).show() },
        onDoiAvatar = { viewModel.doiAvatar(it) },
        onLuuCaiDat = { viewModel.capNhatCaiDat(it) }
    )
}

@Composable
fun GiaoDienCaNhan(
    ten: String, email: String, sdt: String, diaChi: String,
    gioiTinh: String, ngaySinh: String,
    avatarCode: String,
    hangThanhVien: String,
    tongTien: Long,
    nhanThongBao: Boolean,
    hienNutDoiMatKhau: Boolean,
    isLoading: Boolean,
    mucDoHoanThien: Float,
    onDangXuat: () -> Unit, onDoiTen: (String) -> Unit, onDoiMatKhau: (String) -> Unit,
    onLuuThongTin: (String, String, String, String) -> Unit,
    onDoiAvatar: (String) -> Unit,
    onLuuCaiDat: (Boolean) -> Unit
) {
    var hienDialogDoiTen by remember { mutableStateOf(false) }
    var tenMoiNhap by remember { mutableStateOf("") }
    var hienDialogDoiMK by remember { mutableStateOf(false) }
    var matKhauMoi by remember { mutableStateOf("") }
    var xacNhanMatKhauMoi by remember { mutableStateOf("") }
    var hienThiMatKhau by remember { mutableStateOf(false) }
    var hienDialogAvatar by remember { mutableStateOf(false) }
    var hienDialogXacNhanDangXuat by remember { mutableStateOf(false) }
    var hienDialogCaiDat by remember { mutableStateOf(false) }
    var hienDialogHangThanhVien by remember { mutableStateOf(false) }
    var hienDialogThongTin by remember { mutableStateOf(false) }
    var sdtNhap by remember { mutableStateOf("") }
    var diaChiNhap by remember { mutableStateOf("") }
    var gioiTinhNhap by remember { mutableStateOf("Nam") }
    var ngaySinhNhap by remember { mutableStateOf("") }
    val mauHang = when {
        hangThanhVien.contains("Vàng") -> Color(0xFFFFD700)
        hangThanhVien.contains("Bạc") -> Color(0xFFC0C0C0)
        else -> MauCam
    }
    val context = LocalContext.current

    // Dialog Đổi Tên
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
                Column {
                    // Ô mật khẩu mới
                    OutlinedTextField(
                        value = matKhauMoi,
                        onValueChange = { matKhauMoi = it },
                        label = { Text("Mật khẩu mới") },
                        visualTransformation = if (hienThiMatKhau) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = { IconButton({ hienThiMatKhau = !hienThiMatKhau }) { Icon(if (hienThiMatKhau) Icons.Default.VisibilityOff else Icons.Default.Visibility, null) } },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Ô nhập lại mật khẩu
                    OutlinedTextField(
                        value = xacNhanMatKhauMoi,
                        onValueChange = { xacNhanMatKhauMoi = it },
                        label = { Text("Nhập lại mật khẩu") },
                        visualTransformation = if (hienThiMatKhau) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        isError = xacNhanMatKhauMoi.isNotEmpty() && xacNhanMatKhauMoi != matKhauMoi,
                        supportingText = {
                            if (xacNhanMatKhauMoi.isNotEmpty() && xacNhanMatKhauMoi != matKhauMoi) {
                                Text("Mật khẩu không khớp", color = Color.Red, fontSize = 12.sp)
                            }
                        }
                    )
                }
            },
            confirmButton = {
                TextButton({
                    // Logic kiểm tra kỹ càng trước khi lưu
                    if (matKhauMoi.length < 6) {
                        Toast.makeText(context, "Mật khẩu phải từ 6 ký tự", Toast.LENGTH_SHORT).show()
                    } else if (matKhauMoi != xacNhanMatKhauMoi) {
                        Toast.makeText(context, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                    } else {
                        onDoiMatKhau(matKhauMoi)
                        hienDialogDoiMK = false
                        matKhauMoi = ""
                        xacNhanMatKhauMoi = ""
                    }
                }) { Text("Lưu", color = MauCam) }
            },
            dismissButton = {
                TextButton({ hienDialogDoiMK = false; matKhauMoi = ""; xacNhanMatKhauMoi = "" }) { Text("Hủy") }
            },
            containerColor = Color.White
        )
    }

    // Các dialog khác giữ nguyên...
    if (hienDialogThongTin) {
        AlertDialog(
            onDismissRequest = { hienDialogThongTin = false },
            title = { Text("Hồ sơ chi tiết", fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    OutlinedTextField(value = email, onValueChange = {}, label = { Text("Email (Không thể đổi)") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Gray, disabledBorderColor = Color.LightGray))
                    Spacer(Modifier.height(8.dp))
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
    if (hienDialogHangThanhVien) {
        AlertDialog(
            onDismissRequest = { hienDialogHangThanhVien = false },
            title = { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Star, null, tint = mauHang); Spacer(Modifier.width(8.dp)); Text("Hạng thành viên", fontWeight = FontWeight.Bold) } },
            text = { Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) { Text(hangThanhVien, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = mauHang); Spacer(Modifier.height(16.dp)); HorizontalDivider(); Spacer(Modifier.height(16.dp)); Text("Tổng chi tiêu:", fontSize = 14.sp, color = Color.Gray); Text("${tongTien}đ", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MauNauDam) } },
            confirmButton = { TextButton({ hienDialogHangThanhVien = false }) { Text("Đóng", color = MauCam) } }, containerColor = Color.White
        )
    }
    if (hienDialogXacNhanDangXuat) {
        AlertDialog(
            onDismissRequest = { hienDialogXacNhanDangXuat = false },
            title = { Text("Đăng xuất") },
            text = { Text("Bạn có chắc chắn muốn đăng xuất không?") },
            confirmButton = { TextButton({ hienDialogXacNhanDangXuat = false; onDangXuat() }) { Text("Đồng ý", color = Color.Red) } },
            dismissButton = { TextButton({ hienDialogXacNhanDangXuat = false }) { Text("Hủy") } }, containerColor = Color.White
        )
    }
    if (hienDialogCaiDat) {
        AlertDialog(
            onDismissRequest = { hienDialogCaiDat = false },
            title = { Text("Cài đặt ứng dụng", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) { Column { Text("Nhận thông báo", fontWeight = FontWeight.Medium); Text("Nhận tin khuyến mãi", fontSize = 12.sp, color = Color.Gray) }
                        Switch(checked = nhanThongBao, onCheckedChange = { onLuuCaiDat(it) }, colors = SwitchDefaults.colors(checkedThumbColor = MauCam, checkedTrackColor = MauCam.copy(alpha = 0.2f))) }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text("Ngôn ngữ"); Text("Tiếng Việt", color = Color.Gray) }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text("Phiên bản"); Text("1.0.0 (Beta)", color = Color.Gray) }
                }
            },
            confirmButton = { TextButton({ hienDialogCaiDat = false }) { Text("Đóng", color = MauCam) } }, containerColor = Color.White
        )
    }

    // giao diện chính
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9F9F9)).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Thông tin cá nhân", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MauNauDam, modifier = Modifier.padding(top = 16.dp, bottom = 32.dp))

        if (isLoading) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(80.dp).clip(CircleShape).shimmerEffect())
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Box(modifier = Modifier.height(20.dp).fillMaxWidth(0.7f).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.height(15.dp).fillMaxWidth(0.5f).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                }
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Image(painter = painterResource(id = AvatarList.layAnhTuMa(avatarCode)), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(80.dp).clip(CircleShape).border(2.dp, Color.LightGray, CircleShape).clickable { hienDialogAvatar = true })
                    Icon(Icons.Default.CameraAlt, null, modifier = Modifier.size(24.dp).background(Color.White, CircleShape).padding(4.dp), tint = MauCam)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(ten, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MauNauDam)
                    Text(hangThanhVien, fontSize = 15.sp, color = mauHang, fontWeight = FontWeight.Bold)

                    if (mucDoHoanThien < 1.0f) {
                        Spacer(modifier = Modifier.height(8.dp))
                        ThanhHoanThienHoSo(tiLe = mucDoHoanThien)
                    }
                }
                Icon(Icons.Default.Edit, null, tint = Color.Gray, modifier = Modifier.size(24.dp).clickable { tenMoiNhap = ten; hienDialogDoiTen = true })
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        MucChonProfile(Icons.Default.Person, "Hồ sơ cá nhân") {
            sdtNhap = sdt; diaChiNhap = diaChi; gioiTinhNhap = gioiTinh; ngaySinhNhap = ngaySinh; hienDialogThongTin = true
        }
        MucChonProfile(Icons.Default.Star, "Hạng thành viên") { hienDialogHangThanhVien = true }
        if (hienNutDoiMatKhau) MucChonProfile(Icons.Default.Lock, "Thay mật khẩu") { hienDialogDoiMK = true }
        MucChonProfile(Icons.Default.Settings, "Cài đặt") { hienDialogCaiDat = true }
        MucChonProfile(Icons.Default.ExitToApp, "Đăng xuất", true) { hienDialogXacNhanDangXuat = true }
    }
}

@Composable
fun ThanhHoanThienHoSo(tiLe: Float) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Hoàn thiện hồ sơ: ", fontSize = 10.sp, color = Color.Gray)
            Text("${(tiLe * 100).toInt()}%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MauCam)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(fraction = tiLe).fillMaxHeight().clip(RoundedCornerShape(3.dp)).background(MauCam)
            )
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(androidx.compose.ui.unit.IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmer")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = "shimmer_anim"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFEBEBEB),
                Color(0xFFF5F5F5),
                Color(0xFFEBEBEB),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
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
    GiaoDienCaNhan(ten = "Nguyễn Văn A", email = "test@gmail.com", sdt = "0909123456", diaChi = "TP.HCM", gioiTinh = "Nam", ngaySinh = "01/01/2000", avatarCode = "avatar_1", hangThanhVien = "Thành viên Vàng 👑", tongTien = 6500000, nhanThongBao = true, hienNutDoiMatKhau = true, isLoading = false, mucDoHoanThien = 0.6f, onDangXuat = {}, onDoiTen = {}, onDoiMatKhau = {}, onLuuThongTin = { _, _, _, _ -> }, onDoiAvatar = {}, onLuuCaiDat = {})
}