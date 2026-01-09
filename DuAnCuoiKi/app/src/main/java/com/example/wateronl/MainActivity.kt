package com.example.wateronl

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wateronl.ui.theme.WaterOnlTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterOnlTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->

                    NavHost(
                        navController = navController,
                        startDestination = "dang_nhap",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // 1. Màn Đăng Nhập
                        composable("dang_nhap") {
                            ManHinhDangNhap(
                                onChuyenSangDangKy = { navController.navigate("dang_ky") },
                                onDangNhapThanhCong = {
                                    // Đăng nhập xong thì vào trang chủ, xóa luôn lịch sử back về login
                                    navController.navigate("trang_chu") {
                                        popUpTo("dang_nhap") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // 2. Màn Đăng Ký
                        composable("dang_ky") {
                            ManHinhDangKy(
                                onQuayLaiDangNhap = { navController.popBackStack() }
                            )
                        }

                        // 3. Màn Trang Chủ
                        composable("trang_chu") {
                            MainScreen(
                                onDangXuat = {
                                    // Xử lý đăng xuất: Quay về màn đăng nhập và xóa lịch sử
                                    navController.navigate("dang_nhap") {
                                        popUpTo("trang_chu") { inclusive = true }
                                    }
                                }
                            )
                        }
                        // màn hình giỏ hàng nhấn bạc quay lại trang chủ
                        composable("gio_hang_route") {
                        GioHangScreen(
                            onBackClick = {
                                navController.popBackStack()
                            }
                            )
                        }

                    }
                }
            }
        }
    }
}