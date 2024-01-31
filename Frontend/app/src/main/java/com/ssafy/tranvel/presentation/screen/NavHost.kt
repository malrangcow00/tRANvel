package com.ssafy.tranvel.presentation.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.tranvel.presentation.screen.login.LoginScreen
import com.ssafy.tranvel.presentation.screen.register.navigation.registerGraph

@Composable
fun DemoAppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "login_screen") {

        composable(route = "login_screen") {
            LoginScreen() {
                navController.navigate("register")
            }
        }

        registerGraph(navController)
    }
}