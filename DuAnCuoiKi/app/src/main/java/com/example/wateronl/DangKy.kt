package com.example.wateronl

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ManHinhDangKy(
    onQuayLaiDangNhap: () -> Unit,
    onDangKyThanhCong: () -> Unit,
    viewModel: LoginViewModel = viewModel() // Kết nối với ViewModel để xử lý logic đăng ký
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState() // Theo dõi trạng thái đăng ký
    val isLoading by viewModel.isLoading.collectAsState()   // Theo dõi trạng thái đang xử lý

    // Lắng nghe sự thay đổi của loginState
    LaunchedEffect(loginState) {
        if (loginState == "OK") {   // Nếu ViewModel báo đăng ký thành công
            viewModel.resetState()
            onDangKyThanhCong()     // Chuyển màn hình
        } else if (loginState != null) {        // Nếu có lỗi (ví dụ: Email đã tồn tại)
            Toast.makeText(context, loginState, Toast.LENGTH_SHORT).show()
            viewModel.resetState()
        }
    }

    GiaoDienDangKy(
        isLoading = isLoading,
        onDangKy = { email, pass, ten -> viewModel.dangKyTaiKhoan(email, pass, ten) },
        onQuayLaiDangNhap = onQuayLaiDangNhap
    )
}

@Composable
fun GiaoDienDangKy(
    isLoading: Boolean,
    onDangKy: (String, String, String) -> Unit,
    onQuayLaiDangNhap: () -> Unit
) {
    // Các biến lưu giá trị người dùng nhập
    var tenNguoiDung by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var matKhau by remember { mutableStateOf("") }
    var xacNhanMatKhau by remember { mutableStateOf("") }

    // Các biến lưu thông báo lỗi cho từng ô nhập liệu
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun validateName(input: String) {
        tenNguoiDung = input; nameError =
            if (input.isNotBlank()) null else "Tên không được để trống"
    }

    // Hàm kiểm tra định dạng Email hợp lệ bằng Patterns.EMAIL_ADDRESS
    fun validateEmail(input: String) {
        email = input; emailError =
            if (Patterns.EMAIL_ADDRESS.matcher(input).matches()) null else "Email không hợp lệ"
    }

    // Hàm kiểm tra mật khẩu
    fun validatePassword(input: String) {
        matKhau = input; passwordError =
            if (input.length >= 6) null else "Mật khẩu phải từ 6 ký tự"; if (xacNhanMatKhau.isNotEmpty()) confirmPasswordError =
            if (xacNhanMatKhau == input) null else "Mật khẩu không khớp"
    }

    fun validateConfirmPassword(input: String) {
        xacNhanMatKhau = input; confirmPasswordError =
            if (input == matKhau) null else "Mật khẩu không khớp"
    }

    // Chỉ cho phép bấm nút Đăng ký khi tất cả các ô đều hợp lệ và không trống
    val isFormValid =
        nameError == null && emailError == null && passwordError == null && confirmPasswordError == null && tenNguoiDung.isNotBlank() && email.isNotBlank() && matKhau.isNotBlank() && xacNhanMatKhau.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNenKem)
            .clickable(     // Chạm ra ngoài để ẩn bàn phím
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusManager.clearFocus() }) {
        if (isLoading) CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MauCam
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header
            Column(
                modifier = Modifier
                    .height(220.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
                    Surface(
                        shape = CircleShape,
                        color = MauCam.copy(alpha = 0.2f),
                        modifier = Modifier.fillMaxSize()
                    ) {}
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Chào người mới!",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauNauDam
                )
                Text(
                    "Hãy tham gia cùng chúng tôi",
                    fontSize = 13.sp,
                    color = MauNauDam.copy(alpha = 0.6f)
                )
            }

            // Form
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MauTrangCard,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onQuayLaiDangNhap() },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Đăng nhập",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Đăng ký",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MauNauDam
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(3.dp)
                                    .background(MauCam)
                            )
                        }
                    }

                    O_Nhap_Lieu_Co_Nhan(
                        nhan = "Tên người dùng",
                        giaTri = tenNguoiDung,
                        goiY = "Nhập tên...",
                        icon = Icons.Default.Person,
                        loi = nameError,
                        onValueChange = { validateName(it) },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words, //để tự động viết hoa chữ cái đầu
                            imeAction = ImeAction.Next
                        ),
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )

                    O_Nhap_Lieu_Co_Nhan(
                        nhan = "Email",
                        giaTri = email,
                        goiY = "...@gmail.com",
                        icon = Icons.Default.Email,
                        loi = emailError,
                        onValueChange = { validateEmail(it) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )

                    O_Nhap_Lieu_Co_Nhan(
                        nhan = "Mật khẩu",
                        giaTri = matKhau,
                        goiY = "Nhập mật khẩu...",
                        icon = Icons.Default.Lock,
                        loi = passwordError,
                        isPassword = true, // ẩn ký tự bằng dấu chấm
                        onValueChange = { validatePassword(it) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )

                    O_Nhap_Lieu_Co_Nhan(
                        nhan = "Xác nhận mật khẩu",
                        giaTri = xacNhanMatKhau,
                        goiY = "Nhập lại mật khẩu...",
                        icon = Icons.Default.Lock,
                        loi = confirmPasswordError,
                        isPassword = true,
                        onValueChange = { validateConfirmPassword(it) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        onDone = {
                            focusManager.clearFocus()
                            if (isFormValid) {
                                keyboardController?.hide(); onDangKy(
                                    email.trim(),
                                    matKhau,
                                    tenNguoiDung.trim()
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            keyboardController?.hide(); onDangKy(
                            email.trim(),
                            matKhau,
                            tenNguoiDung.trim()
                        )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(
                                8.dp,
                                RoundedCornerShape(16.dp),
                                spotColor = if (isFormValid) MauCam else Color.Gray
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MauCam,
                            disabledContainerColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !isLoading && isFormValid
                    ) {
                        Text(
                            "Đăng ký",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.White)
                    }
                    Spacer(modifier = Modifier.height(200.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDangKy() {
    GiaoDienDangKy(isLoading = false, onDangKy = { _, _, _ -> }, onQuayLaiDangNhap = {})
}