package com.example.wateronl

import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun ManHinhDangNhap(
    onChuyenSangDangKy: () -> Unit,
    onDangNhapThanhCong: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("568231836814-3121ks3amj3219a2sts8e9ujc8c879ot.apps.googleusercontent.com")
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { viewModel.dangNhapGoogle(it) }
            } catch (e: ApiException) {
                Toast.makeText(context, "Lỗi Google: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            }
        }

    LaunchedEffect(loginState) {
        if (loginState == "OK") {
            Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
            viewModel.resetState()
            onDangNhapThanhCong()
        } else if (loginState != null) {
            Toast.makeText(context, loginState, Toast.LENGTH_SHORT).show()
            viewModel.resetState()
        }
    }

    GiaoDienDangNhap(
        isLoading = isLoading,
        onDangNhap = { email, pass -> viewModel.dangNhap(email, pass) },
        onChuyenSangDangKy = onChuyenSangDangKy,
        onGoogleLogin = { launcher.launch(googleSignInClient.signInIntent) },
        onQuenMatKhau = { emailCanGui ->
            viewModel.quenMatKhau(
                email = emailCanGui,
                onThanhCong = {
                    Toast.makeText(context, "Đã gửi mail reset!", Toast.LENGTH_SHORT).show()
                },
                onThatBai = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() })
        }
    )
}

@Composable
fun GiaoDienDangNhap(
    isLoading: Boolean,
    onDangNhap: (String, String) -> Unit,
    onChuyenSangDangKy: () -> Unit,
    onGoogleLogin: () -> Unit,
    onQuenMatKhau: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var matKhau by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val isFormValid =
        emailError == null && passwordError == null && email.isNotEmpty() && matKhau.isNotEmpty()

    fun validateEmail(input: String) {
        email = input; emailError =
            if (Patterns.EMAIL_ADDRESS.matcher(input).matches()) null else "Email không hợp lệ"
    }

    fun validatePassword(input: String) {
        matKhau = input; passwordError = if (input.length >= 6) null else "Mật khẩu phải từ 6 ký tự"
    }

    var hienDialogQuenMK by remember { mutableStateOf(false) }
    var emailQuenMK by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (hienDialogQuenMK) {
        AlertDialog(
            onDismissRequest = { hienDialogQuenMK = false },
            title = { Text("Quên mật khẩu?") },
            text = {
                Column {
                    Text("Nhập email:"); Spacer(Modifier.height(8.dp)); OutlinedTextField(
                    value = emailQuenMK,
                    onValueChange = { emailQuenMK = it },
                    modifier = Modifier.fillMaxWidth()
                )
                }
            },
            confirmButton = {
                TextButton({
                    if (emailQuenMK.isNotEmpty()) {
                        onQuenMatKhau(emailQuenMK); hienDialogQuenMK = false
                    }
                }) { Text("Gửi", color = MauCam) }
            },
            dismissButton = { TextButton({ hienDialogQuenMK = false }) { Text("Hủy") } },
            containerColor = Color.White
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNenKem)
            .clickable(
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
                    "Chào mừng trở lại!",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauNauDam
                )
                Text(
                    "Giải khát mọi lúc mọi nơi",
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
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Đăng nhập",
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
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onChuyenSangDangKy() },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Đăng ký",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                    }

                    O_Nhap_Lieu_Co_Nhan(
                        nhan = "Email",
                        giaTri = email,
                        goiY = "...@gmail.com",
                        onValueChange = { validateEmail(it) },
                        icon = Icons.Default.Email,
                        loi = emailError,
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
                        onValueChange = { validatePassword(it) },
                        icon = Icons.Default.Lock,
                        loi = passwordError,
                        isPassword = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        onDone = {
                            focusManager.clearFocus()
                            if (isFormValid) {
                                keyboardController?.hide(); onDangNhap(email, matKhau)
                            }
                        }
                    )

                    TextButton(onClick = {
                        if (email.isNotEmpty()) onQuenMatKhau(email) else hienDialogQuenMK = true
                    }, modifier = Modifier.align(Alignment.End)) {
                        Text("Quên mật khẩu?", color = MauCam, fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = { keyboardController?.hide(); onDangNhap(email, matKhau) },
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
                            "Đăng nhập",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.White)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                        Text(
                            "Hoặc",
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                    }

                    OutlinedButton(
                        onClick = onGoogleLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_google),
                            null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Đăng nhập bằng Google", color = Color.Black, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(200.dp))
                }
            }
        }
    }
}

// hàm nhập liệu chung
@Composable
fun O_Nhap_Lieu_Co_Nhan(
    nhan: String,
    giaTri: String,
    goiY: String,
    onValueChange: (String) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    loi: String? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onNext: (() -> Unit)? = null,
    onDone: (() -> Unit)? = null
) {
    var hienMatKhau by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = nhan,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MauNauDam,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )
        OutlinedTextField(
            value = giaTri,
            onValueChange = onValueChange,
            placeholder = { Text(goiY, color = Color.Gray.copy(alpha = 0.5f), fontSize = 14.sp) },
            leadingIcon = { Icon(icon, null, tint = MauCam) },
            trailingIcon = if (isPassword) {
                {
                    IconButton({
                        hienMatKhau = !hienMatKhau
                    }) {
                        Icon(
                            if (hienMatKhau) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            null,
                            tint = Color.Gray
                        )
                    }
                }
            } else null,
            isError = loi != null,
            visualTransformation = if (isPassword && !hienMatKhau) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { onNext?.invoke() },
                onDone = { onDone?.invoke() }
            ),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MauNenInput,
                unfocusedContainerColor = MauNenInput,
                focusedBorderColor = MauCam,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Red
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (loi != null) {
            Text(
                text = loi,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDangNhap() {
    GiaoDienDangNhap(
        isLoading = false,
        onDangNhap = { _, _ -> },
        onChuyenSangDangKy = {},
        onGoogleLogin = {},
        onQuenMatKhau = {})
}