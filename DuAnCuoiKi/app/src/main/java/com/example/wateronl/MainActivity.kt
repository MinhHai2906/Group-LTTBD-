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
import com.google.firebase.auth.FirebaseAuth

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
                        // Bắt đầu từ màn hình chờ để kiểm tra đăng nhập
                        startDestination = "man_hinh_cho",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Màn hình chờ
                        composable("man_hinh_cho") {
                            ManHinhCho(
                                onDieuHuong = { manHinhTiepTheo ->
                                    navController.navigate(manHinhTiepTheo) {
                                        // Xóa màn hình chờ khỏi lịch sử để không back lại được
                                        popUpTo("man_hinh_cho") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // màn hình đnhap
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

                        // màn đăng ký
                        composable("dang_ky") {
                            ManHinhDangKy(
                                onQuayLaiDangNhap = { navController.popBackStack() }
                            )
                        }

                        // màn trang chủ
                        composable("trang_chu") {
                            MainScreen(
                                onDangXuat = {
                                    // Xử lý đăng xuất: Quay về màn đăng nhập và xóa lịch sử
                                    navController.navigate("dang_nhap") {
                                        popUpTo("trang_chu") { inclusive = true }
                                    }
                                },
                                navController = navController
                            )
                        }

                        // 4. Các màn hình chức năng khác
                        composable("gio_hang_route") {
                            GioHangScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                navController = navController
                            )
                        }

                        composable("thanh_toan") {
                            val currentUser = FirebaseAuth.getInstance().currentUser
                            val userName = currentUser?.displayName?.takeIf { it.isNotBlank() } ?: "Khách"
                            ThanhToan(
                                userName = userName,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}