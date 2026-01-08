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
                                    navController.navigate("trang_chu") {
                                        popUpTo("dang_nhap") { inclusive = true }
                                    }
                                },
                                onQuenMatKhau = { navController.navigate("quen_mk") }
                            )
                        }

                        // 2. Màn Dky
                        composable("dang_ky") {
                            ManHinhDangKy(
                                onQuayLaiDangNhap = { navController.popBackStack() }
                            )
                        }

                        // 3. Màn TChu (Chứa BottomBar)
                        composable("trang_chu") {
                            MainScreen()
                        }

                        // 4. Màn quen mk
                        composable("quen_mk") {
                            ManHinhQuenMK(
                                onQuayLai = { navController.popBackStack() },
                                onGuiYeuCau = {
                                    navController.navigate("xac_thuc_otp")
                                }
                            )
                        }

                        // 5. Màn nhap otp
                        composable("xac_thuc_otp") {
                            ManHinhXacThucOTP(
                                onQuayLai = { navController.popBackStack() },
                                onXacThucThanhCong = {
                                    navController.navigate("mat_khau_moi")
                                }
                            )
                        }

                        // 6. Màn mk mới
                        composable("mat_khau_moi") {
                            ManHinhMatKhauMoi(
                                onQuayLai = { navController.popBackStack() },
                                onDoiMatKhauThanhCong = {
                                    navController.navigate("dang_nhap") {
                                        popUpTo("dang_nhap") { inclusive = true }
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