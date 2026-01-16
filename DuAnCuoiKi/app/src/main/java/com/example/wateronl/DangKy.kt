package com.example.wateronl

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ManHinhDangKy(
    onQuayLaiDangNhap: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState == "OK") {
            Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_LONG).show()
            viewModel.resetState()
            onQuayLaiDangNhap()
        } else if (loginState != null) {
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

// giao diện
@Composable
fun GiaoDienDangKy(
    isLoading: Boolean,
    onDangKy: (String, String, String) -> Unit,
    onQuayLaiDangNhap: () -> Unit
) {
    var tenNguoiDung by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var matKhau by remember { mutableStateOf("") }
    var xacNhanMatKhau by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize().background(MauNenKem).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { focusManager.clearFocus() }
    ) {
        if (isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MauCam)

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier.height(300.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
                    Surface(shape = CircleShape, color = MauCam.copy(alpha = 0.2f), modifier = Modifier.fillMaxSize()) {}
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", contentScale = ContentScale.Fit, modifier = Modifier.size(100.dp).clip(CircleShape))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Chào người mới!", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MauNauDam)
                Text(text = "Hãy tham gia cùng chúng tôi", fontSize = 14.sp, color = MauNauDam.copy(alpha = 0.6f), textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
            }

            Surface(modifier = Modifier.fillMaxWidth().weight(1f), color = MauTrangCard, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp), shadowElevation = 10.dp) {
                Column(modifier = Modifier.padding(32.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                        Column(modifier = Modifier.weight(1f).clickable { onQuayLaiDangNhap() }, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Đăng nhập", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        }
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Đăng ký", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MauNauDam)
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier.width(80.dp).height(3.dp).background(MauCam))
                        }
                    }

                    O_Nhap_Lieu_Tuy_Chinh(value = tenNguoiDung, onValueChange = { tenNguoiDung = it }, placeholder = "Tên người dùng", icon = Icons.Default.Person)
                    O_Nhap_Lieu_Tuy_Chinh(value = email, onValueChange = { email = it }, placeholder = "Địa chỉ email", icon = Icons.Default.Email, keyboardType = KeyboardType.Email)
                    O_Nhap_Lieu_Tuy_Chinh(value = matKhau, onValueChange = { matKhau = it }, placeholder = "Mật khẩu", icon = Icons.Default.Lock, isPassword = true)
                    O_Nhap_Lieu_Tuy_Chinh(value = xacNhanMatKhau, onValueChange = { xacNhanMatKhau = it }, placeholder = "Xác nhận mật khẩu", icon = Icons.Default.Lock, isPassword = true, imeAction = ImeAction.Done, onAction = { focusManager.clearFocus() })

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            val cleanEmail = email.trim()
                            if (cleanEmail.isEmpty() || matKhau.isEmpty()) Toast.makeText(context, "Thiếu thông tin!", Toast.LENGTH_SHORT).show()
                            else if (matKhau != xacNhanMatKhau) Toast.makeText(context, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show()
                            else onDangKy(cleanEmail, matKhau, tenNguoiDung)
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp).shadow(10.dp, RoundedCornerShape(16.dp), spotColor = MauCam),
                        colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !isLoading
                    ) {
                        Text(text = "Đăng ký", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

// O Nhap Lieu giu nguyen nhu cu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun O_Nhap_Lieu_Tuy_Chinh(value: String, onValueChange: (String) -> Unit, placeholder: String, icon: ImageVector, isPassword: Boolean = false, keyboardType: KeyboardType = KeyboardType.Text, capitalization: KeyboardCapitalization = KeyboardCapitalization.None, imeAction: ImeAction = ImeAction.Next, onAction: () -> Unit = {}) {
    var hienMatKhau by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    TextField(
        value = value, onValueChange = onValueChange, placeholder = { Text(text = placeholder, color = MauNauDam.copy(alpha = 0.4f)) }, leadingIcon = { Icon(imageVector = icon, contentDescription = null, tint = MauCam) },
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType, capitalization = capitalization, imeAction = imeAction),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }, onDone = { focusManager.clearFocus(); onAction() }),
        trailingIcon = if (isPassword) { { IconButton(onClick = { hienMatKhau = !hienMatKhau }) { Icon(imageVector = if (hienMatKhau) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = null, tint = MauNauDam.copy(alpha = 0.4f)) } } } else null,
        visualTransformation = if (isPassword && !hienMatKhau) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(focusedContainerColor = MauNenInput, unfocusedContainerColor = MauNenInput, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, cursorColor = MauCam),
        shape = RoundedCornerShape(16.dp), singleLine = true, modifier = Modifier.fillMaxWidth().height(56.dp)
    )
}

// --- PHAN 3: PREVIEW ---
@Preview(showBackground = true)
@Composable
fun PreviewDangKy() {
    GiaoDienDangKy(isLoading = false, onDangKy = { _,_,_ -> }, onQuayLaiDangNhap = {})
}