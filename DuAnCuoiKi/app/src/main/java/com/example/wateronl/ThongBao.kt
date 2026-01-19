package com.example.wateronl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ThongBaoApp {
    private val _trangThai = MutableStateFlow<ThongBaoState?>(null)
    val trangThai = _trangThai.asStateFlow()

    fun hienLoi(noiDung: String) {
        _trangThai.value = ThongBaoState(noiDung, false)
    }

    fun hienThanhCong(noiDung: String) {
        _trangThai.value = ThongBaoState(noiDung, true)
    }

    fun an() { _trangThai.value = null }
}

data class ThongBaoState(val noiDung: String, val isSuccess: Boolean)

@Composable
fun SnackbarThongBao() {
    val trangThai by ThongBaoApp.trangThai.collectAsState()

    LaunchedEffect(trangThai) {
        if (trangThai != null) {
            delay(3000)
            ThongBaoApp.an()
        }
    }

    if (trangThai != null) {
        val mauNen = if (trangThai!!.isSuccess) Color(0xFF4CAF50) else Color(0xFFE53935)
        val icon = if (trangThai!!.isSuccess) Icons.Default.CheckCircle else Icons.Default.Error

        Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp, start = 20.dp, end = 20.dp), contentAlignment = Alignment.TopCenter) {
            Row(
                modifier = Modifier
                    .background(mauNen, RoundedCornerShape(12.dp))
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Text(trangThai!!.noiDung, color = Color.White, fontSize = 14.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Medium)
            }
        }
    }
}