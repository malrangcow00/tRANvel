package com.ssafy.tranvel.presentation.screen.register.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
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
            PasswordSettingScreen(
                viewModel = hiltViewModel(navController.previousBackStackEntry!!)
            ) {
                navController.navigate("nickname_setting_screen") {
                }
            }
        }

        composable(route = "nickname_setting_screen") {
            NickNameSettingScreen(
                viewModel = hiltViewModel(navController.previousBackStackEntry!!)
            ) {
                navController.navigate("profile_image_setting_screen") {
                }
            }
        }

        composable(route = "profile_image_setting_screen") {
            ProfileImageSettingScreen(
                viewModel = hiltViewModel(navController.previousBackStackEntry!!)
            ) {
                navController.navigate("login_screen") {
                }
            }
        }
    }
}