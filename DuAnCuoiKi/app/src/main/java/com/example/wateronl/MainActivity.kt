package com.example.wateronl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wateronl.ui.theme.WaterOnlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterOnlTheme {
                // Tạo một bộ điều khiển chuyển hướng
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // NavHost là nơi chứa các màn hình sẽ thay đổi
                    NavHost(
                        navController = navController,
                        startDestination = "dang_nhap",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // 1. Màn Đăng Nhập
                        composable("dang_nhap") {
                            ManHinhDangNhap(
                                onChuyenSangDangKy = { navController.navigate("dang_ky") },
                                onDangNhapThanhCong = {
                                    navController.navigate("trang_chu") {
                                        popUpTo("dang_nhap") { inclusive = true }
                                    }
                                },
                                onQuenMatKhau = { navController.navigate("quen_mk") }
                            )
                        }

                        // Màn hình Đăng ký
                        composable("dang_ky") {
                            ManHinhDangKy(
                                onQuayLaiDangNhap = { navController.popBackStack() }
                            )
                        }

                        // Màn hình Trang chủ
                        composable("trang_chu") {
                            ManHinhTrangChu()
                        }
                        composable("quen_mk") {
                            ManHinhQuenMK(
                                onQuayLai = { navController.popBackStack() },
                                onGuiYeuCau = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}