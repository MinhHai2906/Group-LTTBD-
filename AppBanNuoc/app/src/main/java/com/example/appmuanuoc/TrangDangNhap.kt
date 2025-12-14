package com.example.appmuanuoc

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmuanuoc.ui.theme.AppMuaNuocTheme


@Composable
fun TrangDangNhapScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDF6E3)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "LogoQuan",
                modifier = Modifier
                    .padding(top = 50.dp)
                    .size(150.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .border(BorderStroke(2.dp, Color.White), CircleShape)
                    .clip(CircleShape)
            )
            Text(
                text = "Xin chào!",
                modifier = Modifier.padding(top = 30.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                fontFamily = FontFamily.Cursive,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Thưởng thức cafe"
            )
        }

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(450.dp),
            shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)

        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Đăng nhập",
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(140.dp)
                                .height(3.dp)
                                .background(Color(0xFFD4A373))

                        )
                    }
                    Text(
                        text = "Đăng ký",
                        color = Color.Gray,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { onRegisterClick() }
                    )

                }// Đóng khung Row
                Spacer(modifier = Modifier.height(15.dp))
                TextInput(
                    text = email,
                    onValueChange = { email = it },
                    label = "Nhập Email",
                    leadingIcon = {Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint=Color(0xFFD4A373)
                    )}
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextInput(
                    text = password,
                    onValueChange = { password = it },
                    label = "Nhập Mật khẩu",
                    leadingIcon = {Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock Icon"
                    ,tint = Color(0xFFD4A373)
                    )}
                )
                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Quên mật khẩu?",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = { onLoginClick() },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors
                        (containerColor = Color(0xFF8B4513),
                        )


                ) {
                    Text(
                        text = "Đăng nhập",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }//button

            }//Column
        }//Card

    }//Box
}


@Composable
fun TextInput(text:String , onValueChange: (String) -> Unit, label: String, leadingIcon: @Composable (() -> Unit)? = null) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(18.dp)),
        value = text,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = leadingIcon,
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(

            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,


            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,

            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Gray
        )
    )
}

@Preview(showBackground = true, showSystemUi =true)
@Composable
fun TrangDangNhapScreenPreview() {
    AppMuaNuocTheme {
        TrangDangNhapScreen(
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}
