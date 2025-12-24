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
                // Tạo bộ điều khiển chuyển hướng
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // NavHost chứa các màn hình
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

                        // 2. Màn Đăng Ký
                        composable("dang_ky") {
                            ManHinhDangKy(
                                onQuayLaiDangNhap = { navController.popBackStack() }
                            )
                        }

                        // 3. Màn Trang Chủ
                        composable("trang_chu") {
                            ManHinhTrangChu()
                        }

                        // 4. Màn Quên Mật Khẩu
                        composable("quen_mk") {
                            ManHinhQuenMK(
                                onQuayLai = { navController.popBackStack() },
                                onGuiYeuCau = {
                                    // Chuyển sang màn nhập OTP khi bấm nút gửi
                                    navController.navigate("xac_thuc_otp")
                                }
                            )
                        }

                        // 5. Màn Nhập OTP
                        composable("xac_thuc_otp") {
                            ManHinhXacThucOTP(
                                onQuayLai = { navController.popBackStack() },
                                onXacThucThanhCong = {
                                    // Sửa: Xác thực xong thì đi tiếp tới đổi mật khẩu
                                    navController.navigate("mat_khau_moi")
                                }
                            )
                        }

                        // 6. Màn Mật Khẩu Mới
                        composable("mat_khau_moi") {
                            ManHinhMatKhauMoi(
                                onQuayLai = { navController.popBackStack() },
                                onDoiMatKhauThanhCong = {
                                    // Đổi xong thì về màn Đăng nhập và xóa hết lịch sử cũ
                                    navController.navigate("dang_nhap") {
                                        popUpTo("dang_nhap") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}