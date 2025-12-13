package com.example.appmuanuoc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val MauNenKem = Color(0xFFFDF6E3)
val MauTrangCard = Color(0xFFFFFFFF)

@Composable
fun ManHinhDangKy() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNenKem) // to nen thanh mau kem
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally // can giua
        ) {
            Spacer(modifier = Modifier.weight(0.3f)) // 30% MAN HINH
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f), // 70% MAN HINH
                color = MauTrangCard,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),

                ) {

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDangKy() {
    ManHinhDangKy()
}