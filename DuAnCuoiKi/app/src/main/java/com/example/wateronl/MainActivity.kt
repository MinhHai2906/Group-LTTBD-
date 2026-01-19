package com.example.wateronl

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wateronl.ui.theme.WaterOnlTheme
import com.google.firebase.auth.FirebaseAuth
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Configuration.getInstance().userAgentValue = packageName

        setContent {
            WaterOnlTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "man_hinh_cho",
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // 1. Màn hình chờ
                            composable("man_hinh_cho") {
                                ManHinhCho(
                                    onDieuHuong = { manHinhTiepTheo ->
                                        navController.navigate(manHinhTiepTheo) {
                                            popUpTo("man_hinh_cho") { inclusive = true }
                                        }
                                    }
                                )
                            }

                            // 2. Màn hình Đăng nhập
                            composable("dang_nhap") {
                                ManHinhDangNhap(
                                    onChuyenSangDangKy = { navController.navigate("dang_ky") },
                                    onDangNhapThanhCong = {
                                        navController.navigate("trang_chu") {
                                            popUpTo("dang_nhap") { inclusive = true }
                                        }
                                        ThongBaoApp.hienThanhCong("Chào mừng bạn trở lại!")
                                    }
                                )
                            }

                            // 3. Màn hình Đăng ký
                            composable("dang_ky") {
                                ManHinhDangKy(
                                    onQuayLaiDangNhap = { navController.popBackStack() }
                                )
                            }

                            // 4. Trang chủ
                            composable("trang_chu") {
                                MainScreen(
                                    onDangXuat = {
                                        navController.navigate("dang_nhap") {
                                            popUpTo("trang_chu") { inclusive = true }
                                        }
                                        ThongBaoApp.hienThanhCong("Đã đăng xuất thành công")
                                    },
                                    navController = navController
                                )
                            }

                            // 5. Giỏ hàng
                            composable("gio_hang_route") {
                                GioHangScreen(
                                    onBackClick = { navController.popBackStack() },
                                    navController = navController
                                )
                            }

                            // 6. Thanh toán
                            composable("thanh_toan") {
                                val currentUser = FirebaseAuth.getInstance().currentUser
                                val userName = currentUser?.displayName?.takeIf { it.isNotBlank() } ?: "Khách"
                                ThanhToan(
                                    userName = userName,
                                    onBackClick = { navController.popBackStack() },
                                    navController = navController
                                )
                            }

                            // 7. Map
                            composable(
                                route = "map_screen?initialAddress={initialAddress}",
                                arguments = listOf(navArgument("initialAddress") {
                                    type = NavType.StringType
                                    nullable = true
                                })
                            ) { backStackEntry ->
                                val initialAddress = backStackEntry.arguments?.getString("initialAddress")
                                MapScreen(
                                    initialAddress = initialAddress,
                                    onAddressSelected = {
                                        navController.previousBackStackEntry?.savedStateHandle?.set("selected_address", it)
                                        navController.popBackStack()
                                    },
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                        Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                            SnackbarThongBao()
                        }
                    }
                }
            }
        }
    }
}