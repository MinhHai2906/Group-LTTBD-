package com.example.appmuanuoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.appmuanuoc.ui.theme.AppMuaNuocTheme

class TrangChu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppMuaNuocTheme {
                TrangChuApp()
            }
        }
    }
}
@Composable
fun TrangChuApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        layoutType = NavigationSuiteType.NavigationBar,
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.CART -> CartScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.PROFILE -> ProfileScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Trang chủ", Icons.Default.Home),
    CART("Giỏ hàng", Icons.Default.ShoppingCart),
    PROFILE("Hồ sơ cá nhân", Icons.Default.Person),
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Đây là Trang chủ",
        modifier = modifier
    )
}

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Đây là Giỏ hàng",
        modifier = modifier
    )
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Đây là Hồ sơ cá nhân",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrangChuAppPreview() {
    AppMuaNuocTheme {
        TrangChuApp()
    }
}
