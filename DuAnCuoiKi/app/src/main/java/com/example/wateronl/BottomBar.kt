package com.example.wateronl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


private val NavItems = listOf(
    NavItem("Trang chủ", Icons.Default.Home, "home"),
    NavItem("Giỏ hàng", Icons.Default.ShoppingCart, "cart"),
    NavItem("Đơn hàng", Icons.Default.Description, "orders"),
    NavItem("Cá nhân", Icons.Default.Person, "canhan")
)

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onDangXuat: () -> Unit,
    navController: NavController,
    initialTab: Int = 0
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(initialTab) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            ThanhBottomBar(
                selectedIndex = selectedIndex,
                onItemSelected = { index -> selectedIndex = index }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            when (selectedIndex) {
                0 -> TrangChuContent(navController = navController)
                1 -> GioHangScreen(
                    onBackClick = { selectedIndex = 0 },
                    navController = navController
                )
                // --- SỬA ĐOẠN NÀY ---
                2 -> LichSuDonHang(
                    navController = navController,
                    hienNutBack = false, // Ở BottomBar thì không hiện nút Back
                    onItemClick = { maDonHang ->
                        // Khi bấm vào một đơn hàng -> Chuyển sang màn hình Chi tiết
                        navController.navigate("chi_tiet_don_hang/$maDonHang")
                    }
                )
                // --------------------

                3 -> ManHinhCaNhan(
                    navController = navController,
                    onDangXuat = onDangXuat
                )
            }
        }
    }
}

@Composable
fun ThanhBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color.Transparent,
        tonalElevation = 0.dp,
        modifier = Modifier.height(65.dp)
    ) {
        NavItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label,
                        modifier = Modifier.offset(y = 9.dp)
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        fontSize = 10.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MauCam,
                    selectedTextColor = MauCam,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MaterialTheme {
        MainScreen(onDangXuat = {}, navController = rememberNavController())
    }
}