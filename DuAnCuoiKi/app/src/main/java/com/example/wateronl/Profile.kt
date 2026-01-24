package com.example.wateronl

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

enum class LoaiSheet {
    DOI_TEN, DOI_MAT_KHAU, DOI_AVATAR, THONG_TIN_CHI_TIET, CAI_DAT, HANG_THANH_VIEN, XOA_TAI_KHOAN
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManHinhCaNhan(
    navController: NavController,
    onDangXuat: () -> Unit,
    viewModel: ProfileViewModel = viewModel()       // Kết nối với ProfileViewModel để lấy/cập nhật dữ liệu
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
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
    val daXacThuc by viewModel.daXacThucEmail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.kiemTraTrangThaiEmail()
    }

    var loaiSheetHienTai by remember { mutableStateOf<LoaiSheet?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var hienDialogDangXuat by remember { mutableStateOf(false) }
    var tempTen by remember { mutableStateOf("") }
    var tempSdt by remember { mutableStateOf("") }
    var tempDiaChi by remember { mutableStateOf("") }
    var tempNgaySinh by remember { mutableStateOf("") }
    var tempGioiTinh by remember { mutableStateOf("Nam") }
    var tempMatKhauMoi by remember { mutableStateOf("") }
    var tempXacNhanMatKhau by remember { mutableStateOf("") }
    var tempMatKhauXoa by remember { mutableStateOf("") }
    var hienThiMK by remember { mutableStateOf(false) }

    // Regex kiểm tra
    val sdtRegex = Regex("^0\\d{9}$")
    val ngaySinhRegex = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d$")
    val ngaySinhGanDungRegex = Regex("^\\d{1,2}/\\d{1,2}/\\d{4}$")

    val sdtHopLe = tempSdt.isEmpty() || sdtRegex.matches(tempSdt)
    val ngaySinhHopLe = tempNgaySinh.isEmpty() || ngaySinhRegex.matches(tempNgaySinh)

    val mauHang = when {
        hangThanhVien.contains("Vàng") -> Color(0xFFFFD700)
        hangThanhVien.contains("Bạc") -> Color(0xFFC0C0C0)
        else -> MauCam
    }

    fun moSheet(loai: LoaiSheet) {
        tempTen = ten; tempSdt = sdt; tempDiaChi = diaChi
        tempNgaySinh = ngaySinh; tempGioiTinh = gioiTinh
        tempMatKhauMoi = ""; tempXacNhanMatKhau = ""; tempMatKhauXoa = ""
        hienThiMK = false
        loaiSheetHienTai = loai
    }

    Scaffold(
        containerColor = Color(0xFFF9F9F9),
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { focusManager.clearFocus() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 0.dp)
                .verticalScroll(rememberScrollState()),     // Cho phép cuộn trang
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Thông tin cá nhân",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MauCam,
                modifier = Modifier.padding(top = 20.dp, bottom = 32.dp)
            )

            if (isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .shimmerEffect()        // Xử lý trạng thái Loading bằng hiệu ứng Shimmer (vết mờ chuyển động)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .fillMaxWidth(0.7f)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth(0.5f)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Image(
                            painter = painterResource(id = AvatarList.layAnhTuMa(avatarCode)),
                            contentDescription = null, contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.LightGray, CircleShape)
                                .clickable { moSheet(LoaiSheet.DOI_AVATAR) }        // Nhấn vào để đổi ảnh
                        )
                        Icon(
                            Icons.Default.CameraAlt,
                            null,
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.White, CircleShape)
                                .padding(4.dp),
                            tint = MauCam
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = ten,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MauNauDam,
                            modifier = Modifier.clickable { moSheet(LoaiSheet.DOI_TEN) }
                        )

                        Text(
                            hangThanhVien,
                            fontSize = 14.sp,
                            color = mauHang,
                            fontWeight = FontWeight.Bold
                        )

                        if (!daXacThuc && !laGoogle) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.clickable {
                                viewModel.guiLaiEmailXacThuc(
                                    onThanhCong = { ThongBaoApp.hienThanhCong("Đã gửi lại email!") },
                                    onThatBai = { ThongBaoApp.hienLoi(it) }
                                )
                            }, verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Error,
                                    null,
                                    tint = Color.Red,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Chưa xác thực email (Bấm để gửi lại)",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Hiển thị thanh tiến trình hoàn thiện hồ sơ
                        if (mucDoHoanThien < 1.0f) {
                            Spacer(modifier = Modifier.height(8.dp))
                            ThanhHoanThienHoSo(tiLe = mucDoHoanThien)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Các mục lựa chọn (Hồ sơ, Đổi MK, Đăng xuất...)
            MucChonProfile(
                Icons.Default.Person,
                "Hồ sơ cá nhân"
            ) { moSheet(LoaiSheet.THONG_TIN_CHI_TIET) }
            MucChonProfile(
                Icons.Default.Star,
                "Hạng thành viên"
            ) { moSheet(LoaiSheet.HANG_THANH_VIEN) }
            if (!laGoogle) MucChonProfile(
                Icons.Default.Lock,
                "Đổi mật khẩu"
            ) { moSheet(LoaiSheet.DOI_MAT_KHAU) }

            MucChonProfile(Icons.Default.Settings, "Cài đặt") { moSheet(LoaiSheet.CAI_DAT) }
            MucChonProfile(Icons.Default.ExitToApp, "Đăng xuất", true) { hienDialogDangXuat = true }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Cửa sổ trượt lên (Bottom Sheet) dùng chung cho nhiều chức năng
        if (loaiSheetHienTai != null) {
            ModalBottomSheet(
                onDismissRequest = { loaiSheetHienTai = null },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { focusManager.clearFocus() }
                        .padding(24.dp)
                        .padding(bottom = 20.dp)
                ) {
                    when (loaiSheetHienTai) {
                        LoaiSheet.DOI_TEN -> {
                            Text(
                                "Đổi tên hiển thị",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MauNauDam
                            )
                            Spacer(Modifier.height(16.dp))
                            OutlinedTextField(
                                value = tempTen,
                                onValueChange = { tempTen = it },
                                label = { Text("Tên của bạn") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            Spacer(Modifier.height(24.dp))
                            Button(
                                onClick = {
                                    viewModel.capNhatHoTen(tempTen); loaiSheetHienTai = null
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "Lưu thay đổi",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        LoaiSheet.DOI_AVATAR -> {
                            Text(
                                "Chọn ảnh đại diện",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MauNauDam
                            )
                            Spacer(Modifier.height(16.dp))
                            // Hiển thị danh sách Avatar dạng lưới để người dùng chọn
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(4),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(AvatarList.danhSach.toList()) { (ma, idAnh) ->
                                    Image(
                                        painter = painterResource(id = idAnh),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(70.dp)
                                            .clip(CircleShape)
                                            .border(
                                                3.dp,
                                                if (avatarCode == ma) MauCam else Color.Transparent,
                                                CircleShape
                                            )
                                            .clickable {
                                                viewModel.doiAvatar(ma); loaiSheetHienTai = null
                                            },
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            Spacer(Modifier.height(30.dp))
                        }

                        LoaiSheet.THONG_TIN_CHI_TIET -> {
                            Text(
                                "Hồ sơ chi tiết",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MauNauDam
                            )
                            Spacer(Modifier.height(16.dp))
                            OutlinedTextField(
                                value = email,
                                onValueChange = {},
                                label = { Text("Email") },
                                readOnly = true,
                                enabled = false,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            Spacer(Modifier.height(12.dp))
                            OutlinedTextField(
                                value = tempSdt,
                                onValueChange = { if (it.length <= 10) tempSdt = it },
                                label = { Text("Số điện thoại") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                isError = !sdtHopLe,
                                supportingText = {
                                    if (!sdtHopLe && tempSdt.isNotEmpty()) Text("SĐT phải bắt đầu bằng 0 và có 10 chữ số", color = Color.Red)
                                }
                            )
                            Spacer(Modifier.height(12.dp))
                            OutlinedTextField(
                                value = tempDiaChi,
                                onValueChange = { tempDiaChi = it },
                                label = { Text("Địa chỉ") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            Spacer(Modifier.height(12.dp))
                            OutlinedTextField(
                                value = tempNgaySinh,
                                onValueChange = { tempNgaySinh = it },
                                label = { Text("Ngày sinh (dd/mm/yyyy)") },
                                placeholder = { Text("VD: 01/01/2005") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                isError = !ngaySinhHopLe,
                                supportingText = {
                                    if (!ngaySinhHopLe && tempNgaySinh.isNotEmpty()) {
                                        val message = if (ngaySinhGanDungRegex.matches(tempNgaySinh)) {
                                            "Thiếu số 0 (VD: xx/xx/xxxx)"
                                        } else {
                                            "Định dạng đúng: dd/mm/yyyy"
                                        }
                                        Text(message, color = Color.Red)
                                    }
                                }
                            )
                            Spacer(Modifier.height(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Giới tính:", fontWeight = FontWeight.Medium)
                                Spacer(Modifier.width(16.dp))
                                FilterChip(
                                    selected = tempGioiTinh == "Nam",
                                    onClick = { tempGioiTinh = "Nam" },
                                    label = { Text("Nam") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MauCam.copy(alpha = 0.2f),
                                        selectedLabelColor = MauNauDam
                                    )
                                )
                                Spacer(Modifier.width(8.dp))
                                FilterChip(
                                    selected = tempGioiTinh == "Nữ",
                                    onClick = { tempGioiTinh = "Nữ" },
                                    label = { Text("Nữ") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MauCam.copy(alpha = 0.2f),
                                        selectedLabelColor = MauNauDam
                                    )
                                )
                            }
                            Spacer(Modifier.height(24.dp))
                            Button(
                                onClick = {
                                    if (tempSdt.isEmpty() || tempDiaChi.isEmpty() || tempNgaySinh.isEmpty()) {
                                        ThongBaoApp.hienLoi("Vui lòng nhập đầy đủ thông tin")
                                    } else if (!sdtHopLe) {
                                        ThongBaoApp.hienLoi("Số điện thoại không hợp lệ")
                                    } else if (!ngaySinhHopLe) {
                                        val loiChiTiet = if (ngaySinhGanDungRegex.matches(tempNgaySinh)) 
                                            "Vui lòng thêm số 0 vào ngày/tháng!" 
                                            else "Ngày sinh không đúng định dạng dd/mm/yyyy"
                                        ThongBaoApp.hienLoi(loiChiTiet)
                                    } else {
                                        viewModel.capNhatThongTinChiTiet(
                                            tempSdt,
                                            tempDiaChi,
                                            tempGioiTinh,
                                            tempNgaySinh
                                        ); loaiSheetHienTai =
                                        null; ThongBaoApp.hienThanhCong("Đã lưu hồ sơ")
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Lưu hồ sơ", fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        LoaiSheet.DOI_MAT_KHAU -> {
                            Text(
                                "Đổi mật khẩu",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MauNauDam
                            )
                            Spacer(Modifier.height(16.dp))
                            OutlinedTextField(
                                value = tempMatKhauMoi,
                                onValueChange = { tempMatKhauMoi = it },
                                label = { Text("Mật khẩu mới") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                visualTransformation = if (hienThiMK) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton({
                                        hienThiMK = !hienThiMK
                                    }) {
                                        Icon(
                                            if (hienThiMK) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            null
                                        )
                                    }
                                }
                            )
                            Spacer(Modifier.height(12.dp))
                            OutlinedTextField(
                                value = tempXacNhanMatKhau,
                                onValueChange = { tempXacNhanMatKhau = it },
                                label = { Text("Nhập lại mật khẩu") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                visualTransformation = if (hienThiMK) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton({
                                        hienThiMK = !hienThiMK
                                    }) {
                                        Icon(
                                            if (hienThiMK) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            null
                                        )
                                    }
                                },
                                isError = tempXacNhanMatKhau.isNotEmpty() && tempXacNhanMatKhau != tempMatKhauMoi
                            )
                            if (tempXacNhanMatKhau.isNotEmpty() && tempXacNhanMatKhau != tempMatKhauMoi) Text(
                                "Mật khẩu không khớp",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                            )
                            Spacer(Modifier.height(24.dp))
                            Button(
                                onClick = {
                                    if (tempMatKhauMoi.length < 6) ThongBaoApp.hienLoi("Mật khẩu quá ngắn")
                                    else if (tempMatKhauMoi != tempXacNhanMatKhau) ThongBaoApp.hienLoi(
                                        "Mật khẩu không khớp"
                                    )
                                    else {
                                        viewModel.doiMatKhau(
                                            tempMatKhauMoi,
                                            {
                                                ThongBaoApp.hienThanhCong("Đổi thành công!"); loaiSheetHienTai =
                                                null
                                            },
                                            { ThongBaoApp.hienLoi(it) })
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "Cập nhật mật khẩu",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        LoaiSheet.HANG_THANH_VIEN -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    null,
                                    tint = mauHang,
                                    modifier = Modifier.size(50.dp)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    hangThanhVien,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = mauHang
                                )
                                Spacer(Modifier.height(16.dp))
                                HorizontalDivider()
                                Spacer(Modifier.height(16.dp))
                                Text("Tổng chi tiêu tích lũy", fontSize = 14.sp, color = Color.Gray)
                                Text(
                                    "${tongTien}đ",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MauNauDam
                                )
                                Spacer(Modifier.height(24.dp))
                                Button(
                                    onClick = { loaiSheetHienTai = null },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MauCam.copy(
                                            alpha = 0.1f
                                        )
                                    ), shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Đóng", fontWeight = FontWeight.Bold, color = MauCam)
                                }
                            }
                        }

                        LoaiSheet.CAI_DAT -> {
                            Text(
                                "Cài đặt",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MauNauDam
                            )
                            Spacer(Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        "Thông báo",
                                        fontWeight = FontWeight.Medium
                                    ); Text(
                                    "Nhận tin khuyến mãi",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                }
                                Switch(
                                    checked = nhanThongBao,
                                    onCheckedChange = { viewModel.capNhatCaiDat(it) },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MauCam,
                                        checkedTrackColor = MauCam.copy(alpha = 0.2f)
                                    )
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = Color.LightGray.copy(alpha = 0.3f)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) { Text("Phiên bản"); Text("1.0.0 (Beta)", color = Color.Gray) }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = Color.LightGray.copy(alpha = 0.3f)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { loaiSheetHienTai = LoaiSheet.XOA_TAI_KHOAN }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.DeleteForever, null, tint = Color.Red)
                                Spacer(Modifier.width(16.dp))
                                Text(
                                    "Xóa tài khoản",
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(Modifier.height(30.dp))
                        }

                        LoaiSheet.XOA_TAI_KHOAN -> {
                            Text(
                                "Xóa tài khoản vĩnh viễn",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "CẢNH BÁO: Hành động này không thể hoàn tác. Toàn bộ dữ liệu, lịch sử đơn hàng và điểm tích lũy của bạn sẽ bị xóa vĩnh viễn.",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Spacer(Modifier.height(16.dp))

                            if (!laGoogle) {
                                OutlinedTextField(
                                    value = tempMatKhauXoa,
                                    onValueChange = { tempMatKhauXoa = it },
                                    label = { Text("Nhập mật khẩu để xác nhận") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    singleLine = true,
                                    visualTransformation = if (hienThiMK) VisualTransformation.None else PasswordVisualTransformation(),
                                    trailingIcon = {
                                        IconButton({ hienThiMK = !hienThiMK }) {
                                            Icon(
                                                if (hienThiMK) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                                null
                                            )
                                        }
                                    }
                                )
                            }
                            Spacer(Modifier.height(24.dp))
                            Button(
                                onClick = {
                                    if (!laGoogle && tempMatKhauXoa.isEmpty()) {
                                        ThongBaoApp.hienLoi("Vui lòng nhập mật khẩu!")
                                    } else {
                                        viewModel.xoaTaiKhoan(
                                            tempMatKhauXoa,
                                            onThanhCong = {
                                                loaiSheetHienTai =
                                                    null; onDangXuat(); ThongBaoApp.hienThanhCong("Đã xóa tài khoản vĩnh viễn")
                                            },
                                            onThatBai = { ThongBaoApp.hienLoi(it) }
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "Xác nhận xóa vĩnh viễn",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    if (hienDialogDangXuat) {
        AlertDialog(
            onDismissRequest = { hienDialogDangXuat = false },
            title = { Text("Đăng xuất?") },
            text = { Text("Bạn có chắc chắn muốn đăng xuất không?") },
            confirmButton = {
                TextButton({
                    hienDialogDangXuat = false; onDangXuat()
                }) { Text("Đồng ý", color = Color.Red) }
            },
            dismissButton = { TextButton({ hienDialogDangXuat = false }) { Text("Hủy") } },
            containerColor = Color.White
        )
    }
}

@Composable
fun ThanhHoanThienHoSo(tiLe: Float) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Hoàn thiện hồ sơ: ", fontSize = 10.sp, color = Color.Gray)
            Text(
                "${(tiLe * 100).toInt()}%",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MauCam
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = tiLe)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(MauCam)
            )
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(androidx.compose.ui.unit.IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmer")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(), targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(1000)), label = "shimmer_anim"
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFFEBEBEB), Color(0xFFF5F5F5), Color(0xFFEBEBEB)),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned { size = it.size }
}

@Composable
fun MucChonProfile(icon: ImageVector, tieuDe: String, isRed: Boolean = false, onClick: () -> Unit) {
    val mauChu = if (isRed) Color.Red else MauNauDam
    Column(modifier = Modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                null,
                tint = if (isRed) Color.Red else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                tieuDe,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = mauChu,
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.LightGray)
        }
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    ManHinhCaNhan(navController = rememberNavController(), onDangXuat = {})
}
