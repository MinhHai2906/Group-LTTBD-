package com.example.wateronl

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

// --- PHAN 1: LOGIC ---
@Composable
fun ManHinhDangNhap(
    onChuyenSangDangKy: () -> Unit,
    onDangNhapThanhCong: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Cấu hình Google Sign In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("568231836814-3121ks3amj3219a2sts8e9ujc8c879ot.apps.googleusercontent.com")
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
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
        // Xu ly logic Quen mat khau o day
        onQuenMatKhau = { emailCanGui ->
            viewModel.quenMatKhau(
                email = emailCanGui,
                onThanhCong = { Toast.makeText(context, "Đã gửi mail reset! Hãy kiểm tra hộp thư.", Toast.LENGTH_LONG).show() },
                onThatBai = { loi -> Toast.makeText(context, loi, Toast.LENGTH_SHORT).show() }
            )
        }
    )
}

// --- PHAN 2: GIAO DIEN ---
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

    // Bien cho Dialog Quen mat khau
    var hienDialogQuenMK by remember { mutableStateOf(false) }
    var emailQuenMK by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    // --- DIALOG QUEN MAT KHAU ---
    if (hienDialogQuenMK) {
        AlertDialog(
            onDismissRequest = { hienDialogQuenMK = false },
            title = { Text("Quên mật khẩu?", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Nhập email để nhận link đặt lại mật khẩu:", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = emailQuenMK,
                        onValueChange = { emailQuenMK = it },
                        label = { Text("Email của bạn") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (emailQuenMK.isNotEmpty()) {
                        onQuenMatKhau(emailQuenMK)
                        hienDialogQuenMK = false
                    } else {
                        Toast.makeText(context, "Vui lòng nhập email", Toast.LENGTH_SHORT).show()
                    }
                }) { Text("Gửi", color = MauCam) }
            },
            dismissButton = {
                TextButton(onClick = { hienDialogQuenMK = false }) { Text("Hủy", color = Color.Gray) }
            },
            containerColor = Color.White
        )
    }
    // ----------------------------

    Box(modifier = Modifier.fillMaxSize().background(MauNenKem).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { focusManager.clearFocus() }) {
        if (isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MauCam)

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            // Header (Logo)
            Column(modifier = Modifier.height(300.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
                    Surface(shape = CircleShape, color = MauCam.copy(alpha = 0.2f), modifier = Modifier.fillMaxSize()) {}
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", contentScale = ContentScale.Fit, modifier = Modifier.size(100.dp).clip(CircleShape))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Chào mừng bạn đã trở lại!", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MauNauDam)
                Text(text = "Giải khát mọi lúc mọi nơi", fontSize = 14.sp, color = MauNauDam.copy(alpha = 0.6f),textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
            }

            // Form nhap lieu
            Surface(modifier = Modifier.fillMaxWidth().weight(1f), color = MauTrangCard, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp), shadowElevation = 10.dp) {
                Column(modifier = Modifier.padding(32.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Tab chuyen doi
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Đăng nhập", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MauNauDam)
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier.width(80.dp).height(3.dp).background(MauCam))
                        }
                        Column(modifier = Modifier.weight(1f).clickable { onChuyenSangDangKy() }, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Đăng ký", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        }
                    }

                    O_Nhap_Lieu_Tuy_Chinh(value = email, onValueChange = { email = it }, placeholder = "Địa chỉ email", icon = Icons.Default.Email, keyboardType = KeyboardType.Email)
                    O_Nhap_Lieu_Tuy_Chinh(value = matKhau, onValueChange = { matKhau = it }, placeholder = "Mật khẩu", icon = Icons.Default.Lock, isPassword = true, imeAction = ImeAction.Done, onAction = { keyboardController?.hide(); onDangNhap(email, matKhau) })

                    // Nut Quen mat khau (SUA LOGIC TAI DAY)
                    TextButton(
                        onClick = {
                            if (email.isNotEmpty()) {
                                // Neu da nhap email o o chinh -> Gui luon
                                onQuenMatKhau(email)
                            } else {
                                // Neu chua nhap -> Hien Dialog
                                hienDialogQuenMK = true
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Quên mật khẩu?", color = MauCam, fontWeight = FontWeight.SemiBold)
                    }

                    // Nut Dang nhap
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            if (email.isEmpty() || matKhau.isEmpty()) Toast.makeText(context, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show()
                            else onDangNhap(email, matKhau)
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp).shadow(10.dp, RoundedCornerShape(16.dp), spotColor = MauCam),
                        colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !isLoading
                    ) {
                        Text(text = "Đăng nhập", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                    }

                    // Hoac dang nhap bang Google
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                        Text(text = "Hoặc", modifier = Modifier.padding(horizontal = 8.dp), color = Color.Gray)
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                    }

                    OutlinedButton(
                        onClick = onGoogleLogin,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                    ) {
                        Image(painter = painterResource(id = R.drawable.ic_google), contentDescription = null, modifier = Modifier.size(24.dp)) // Ban can co anh ic_google trong drawable
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Đăng nhập bằng Google", color = Color.Black, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

// O Nhap Lieu giu nguyen (tai su dung lai ham cu)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun O_Nhap_Lieu_Tuy_Chinh(value: String, onValueChange: (String) -> Unit, placeholder: String, icon: ImageVector, isPassword: Boolean = false, keyboardType: KeyboardType = KeyboardType.Text, imeAction: ImeAction = ImeAction.Next, onAction: () -> Unit = {}) {
    var hienMatKhau by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    TextField(
        value = value, onValueChange = onValueChange, placeholder = { Text(text = placeholder, color = MauNauDam.copy(alpha = 0.4f)) }, leadingIcon = { Icon(imageVector = icon, contentDescription = null, tint = MauCam) },
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }, onDone = { focusManager.clearFocus(); onAction() }),
        trailingIcon = if (isPassword) { { IconButton(onClick = { hienMatKhau = !hienMatKhau }) { Icon(imageVector = if (hienMatKhau) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = null, tint = MauNauDam.copy(alpha = 0.4f)) } } } else null,
        visualTransformation = if (isPassword && !hienMatKhau) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(focusedContainerColor = MauNenInput, unfocusedContainerColor = MauNenInput, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, cursorColor = MauCam),
        shape = RoundedCornerShape(16.dp), singleLine = true, modifier = Modifier.fillMaxWidth().height(56.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDangNhap() {
    GiaoDienDangNhap(isLoading = false, onDangNhap = {_,_ ->}, onChuyenSangDangKy = {}, onGoogleLogin = {}, onQuenMatKhau = {})
}