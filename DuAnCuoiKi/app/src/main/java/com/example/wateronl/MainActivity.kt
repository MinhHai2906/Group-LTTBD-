package com.example.wateronl

import android.annotation.SuppressLint
import android.content.Intent
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
import org.osmdroid.config.Configuration
import vn.zalopay.sdk.ZaloPaySDK

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Configuration.getInstance().userAgentValue = packageName

        // Xử lý kết quả ZaloPay khi Activity được tạo mới
        ZaloPaySDK.getInstance().onResult(intent)

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

                            // 2. Màn hình đăng nhập
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
                                    onQuayLaiDangNhap = { navController.popBackStack() },
                                    onDangKyThanhCong = {
                                        navController.navigate("trang_chu") {
                                            popUpTo("man_hinh_cho") { inclusive = true }
                                        }
                                        ThongBaoApp.hienThanhCong("Đăng ký thành công! Vui lòng kiểm tra email xác thực.")
                                    }
                                )
                            }

                            // 4. Trang chủ
                            composable(
                                route = "trang_chu?tabIndex={tabIndex}",
                                arguments = listOf(androidx.navigation.navArgument("tabIndex") {
                                    type = androidx.navigation.NavType.IntType
                                    defaultValue = 0
                                })
                            ) { backStackEntry ->
                                val tabIndex = backStackEntry.arguments?.getInt("tabIndex") ?: 0

                                MainScreen(
                                    onDangXuat = {
                                        navController.navigate("dang_nhap") {
                                            popUpTo("trang_chu") { inclusive = true }
                                        }
                                        ThongBaoApp.hienThanhCong("Đã đăng xuất thành công")
                                    },
                                    navController = navController,
                                    initialTab = tabIndex
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
                                ThanhToan(
                                    onBackClick = { navController.popBackStack() },
                                    navController = navController,
                                    activity = this@MainActivity
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
                                val initialAddress =
                                    backStackEntry.arguments?.getString("initialAddress")
                                MapScreen(
                                    initialAddress = initialAddress,
                                    onAddressSelected = {
                                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                            "selected_address",
                                            it
                                        )
                                        navController.popBackStack()
                                    },
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            composable("lich_su_don_hang") {
                            }
                            composable(
                                route = "chi_tiet_don_hang/{donHangId}",
                                arguments = listOf(navArgument("donHangId") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val donHangId =
                                    backStackEntry.arguments?.getString("donHangId") ?: ""
                                ChiTietDonHang(navController = navController, donHangId = donHangId)
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Xử lý kết quả ZaloPay khi Activity đã chạy
        ZaloPaySDK.getInstance().onResult(intent)
    }
}
