package com.example.appmuanuoc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.appmuanuoc.Greeting
import com.example.appmuanuoc.TrangChuApp
import com.example.appmuanuoc.ManHinhDangKy
import com.example.appmuanuoc.TrangDangNhapScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "first_screen",
        modifier = modifier
    ) {

        composable("first_screen") {
            Greeting(
                onNavigateToLogin = { navController.navigate("login") }
            )
        }

        // Tuyến đường "login"
        composable("login") {
            TrangDangNhapScreen(
                modifier = Modifier,
                onLoginClick = {

                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate("register") }
            )
        }

        // Tuyến đường "register"
        composable("register") {
            ManHinhDangKy()
        }

        // Tuyến đường "home"
        composable("home") {
            TrangChuApp()
        }
    }
}