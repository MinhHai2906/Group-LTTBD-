package com.example.wateronl

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

// --- PHAN 1: LOGIC (Cai xuong) ---
@Composable
fun ManHinhDangNhap(
    onChuyenSangDangKy: () -> Unit,
    onDangNhapThanhCong: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Cau hinh Google
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("568231836814-3121ks3amj3219a2sts8e9ujc8c879ot.apps.googleusercontent.com") // Thay ma cua ong vao
        .requestEmail()
        .build()
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }
    val googleLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    // Goi phan Giao dien de hien thi
    GiaoDienDangNhap(
        isLoading = isLoading,
        onDangNhap = { e, p -> viewModel.dangNhap(e, p) },
        onDangNhapGoogle = {
            googleSignInClient.signOut()
            googleLauncher.launch(googleSignInClient.signInIntent)
        },
        onQuenMatKhau = { viewModel.quenMatKhau(it) },
        onChuyenSangDangKy = onChuyenSangDangKy
    )
}

// --- PHAN 2: GIAO DIEN (Cai da - De Preview) ---
@Composable
fun GiaoDienDangNhap(
    isLoading: Boolean,
    onDangNhap: (String, String) -> Unit,
    onDangNhapGoogle: () -> Unit,
    onQuenMatKhau: (String) -> Unit,
    onChuyenSangDangKy: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var matKhau by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNenKem)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).zIndex(2f), color = MauCam)
        }

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Column(modifier = Modifier.height(300.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
                    Surface(shape = CircleShape, color = MauCam.copy(alpha = 0.2f), modifier = Modifier.fillMaxSize()) {}
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", contentScale = ContentScale.Fit, modifier = Modifier.size(100.dp).clip(CircleShape))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Chào mừng trở lại!", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MauNauDam)
                Text(text = "Hãy tận hưởng và thưởng thức", fontSize = 14.sp, color = MauNauDam.copy(alpha = 0.6f), textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
            }

            // Body
            Surface(modifier = Modifier.fillMaxWidth().weight(1f), color = MauTrangCard, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp), shadowElevation = 10.dp) {
                Column(modifier = Modifier.padding(32.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
                    O_Nhap_Lieu_Tuy_Chinh(value = matKhau, onValueChange = { matKhau = it }, placeholder = "Mật khẩu", icon = Icons.Default.Lock, isPassword = true, imeAction = ImeAction.Done,
                        onAction = {
                            keyboardController?.hide()
                            if (email.isBlank() || matKhau.isEmpty()) Toast.makeText(context, "Nhập thiếu!", Toast.LENGTH_SHORT).show()
                            else onDangNhap(email.trim(), matKhau)
                        }
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Text(text = "Quên mật khẩu?", modifier = Modifier.clickable { onQuenMatKhau(email) }, textAlign = TextAlign.End, color = MauNauDam.copy(alpha = 0.6f))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            if (email.isBlank() || matKhau.isEmpty()) Toast.makeText(context, "Nhập thiếu!", Toast.LENGTH_SHORT).show()
                            else onDangNhap(email.trim(), matKhau)
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                        shape = RoundedCornerShape(16.dp)
                    ) { Text(text = "Đăng nhập", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White) }

                    OutlinedButton(
                        onClick = onDangNhapGoogle,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
                    ) { Text(text = "Đăng nhập bằng Google", color = Color.Black) }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "Chưa có tài khoản? ", color = MauNauDam.copy(alpha = 0.6f))
                        Text(text = "Đăng ký", modifier = Modifier.clickable { onChuyenSangDangKy() }, style = TextStyle(color = MauCam, fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

// --- PHAN 3: PREVIEW ---
@Preview(showBackground = true)
@Composable
fun PreviewDangNhap() {
    // Goi phan GiaoDien voi du lieu gia de xem thu
    GiaoDienDangNhap(
        isLoading = false,
        onDangNhap = { _, _ -> },
        onDangNhapGoogle = {},
        onQuenMatKhau = {},
        onChuyenSangDangKy = {}
    )
}