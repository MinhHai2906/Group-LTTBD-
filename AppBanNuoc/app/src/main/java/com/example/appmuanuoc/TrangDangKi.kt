package com.example.appmuanuoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
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
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize().padding(top = 200.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tạo tài khoản",
            fontSize = 30.sp)

        Spacer(modifier = Modifier.height(16.dp))

        MyTextField(value = email,
            onValueChange = { email = it },
            label = "Email")

        Spacer(modifier = Modifier.height(8.dp))

        MyTextField(value = password,
            onValueChange = { password = it },
            label = "Mật khẩu")

        Spacer(modifier = Modifier.height(8.dp))

        MyTextField(value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Nhập lại mật khẩu")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* TODO: Xử lý đăng ký */ }) {
            Text("Đăng ký")
        }
    }
}

@Composable
fun MyTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Blue,
        ),
        shape = RoundedCornerShape(18.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrangDangKiScreenPreview() {
    AppMuaNuocTheme {
        TrangDangKiScreen()
    }
}
