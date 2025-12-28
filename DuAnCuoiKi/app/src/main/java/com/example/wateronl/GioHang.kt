package com.example.wateronl

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GioHangScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Giỏ hàng")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGioHang(){
    MaterialTheme {
        GioHangScreen()
    }
}
