package com.ssafy.tranvel.presentation.screen.register.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.tranvel.presentation.screen.home.navigation.navigateHome
import com.ssafy.tranvel.presentation.screen.register.EmailAuthScreen
import com.ssafy.tranvel.presentation.screen.register.NickNameSettingScreen
import com.ssafy.tranvel.presentation.screen.register.PasswordSettingScreen
import com.ssafy.tranvel.presentation.screen.register.ProfileImageSettingScreen

fun NavGraphBuilder.registerGraph(navController: NavController) {
    navigation(startDestination = "email_auth_screen", route = "register") {
        composable(route = "email_auth_screen") {
            EmailAuthScreen(
                viewModel = hiltViewModel(it)
            ) {
                navController.navigate("password_setting_screen") {
                }
            }
        }

        composable(route = "password_setting_screen") {
            val parentEntry = remember(it) {
                navController.getBackStackEntry("email_auth_screen")
            }
            PasswordSettingScreen(
                viewModel = hiltViewModel(parentEntry)
            ) {
                navController.navigate("nickname_setting_screen") {
                }
            }
        }

        composable(route = "nickname_setting_screen") {
            val parentEntry = remember(it) {
                navController.getBackStackEntry("email_auth_screen")
            }
            NickNameSettingScreen(
                viewModel = hiltViewModel(parentEntry)
            ) {
                navController.navigate("login_screen") {
                    navController.navigateHome()
                }
            }
        }
    }
}
