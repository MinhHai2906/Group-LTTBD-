package com.example.wateronl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


private val NavItems = listOf(
    NavItem("Trang chủ", Icons.Default.Home, "home"),
    NavItem("Giỏ hàng", Icons.Default.ShoppingCart, "cart"),
    NavItem("Setting", Icons.Default.Settings, "setting")
)

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(0) }

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
                0 -> TrangChuContent()
                1 -> GioHangScreen()
                2 -> SettingScreen()
            }
        }
    }
}

@Composable
fun ThanhBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        NavItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        fontSize = 10.sp
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MaterialTheme {
        MainScreen()
    }
}
