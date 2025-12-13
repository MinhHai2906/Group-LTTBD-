package com.example.appmuanuoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmuanuoc.ui.theme.AppMuaNuocTheme

class TrangDangKi : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppMuaNuocTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TrangDangKiScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TrangDangKiScreen(modifier: Modifier = Modifier) {
    var hoTen by remember { mutableStateOf("") }
    var sdt by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5EFE6)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Text(
                            text = "Đăng nhập",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Đăng ký",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    MyTextField(value = hoTen, onValueChange = { hoTen = it }, placeholder = "Họ và tên...")
                    Spacer(modifier = Modifier.height(16.dp))
                    MyTextField(value = sdt, onValueChange = { sdt = it }, placeholder = "Nhập SĐT...")
                    Spacer(modifier = Modifier.height(16.dp))
                    MyTextField(value = email, onValueChange = { email = it }, placeholder = "Nhập tài khoản email...")
                    Spacer(modifier = Modifier.height(16.dp))
                    MyTextField(value = password, onValueChange = { password = it }, placeholder = "Nhập mật khẩu...")
                    Spacer(modifier = Modifier.height(16.dp))
                    MyTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, placeholder = "Xác nhận mật khẩu...")
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD2B48C))
                    ) {
                        Text(
                            text = "Tiếp tục",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MyTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,

        ),
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrangDangKiScreenPreview() {
    AppMuaNuocTheme {
        TrangDangKiScreen()
    }
}
