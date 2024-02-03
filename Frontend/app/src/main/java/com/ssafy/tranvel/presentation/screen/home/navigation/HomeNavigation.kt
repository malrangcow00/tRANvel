package com.ssafy.tranvel.presentation.screen.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssafy.tranvel.presentation.screen.home.HomeScreen


const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(homeRoute) {
        HomeScreen(
            { navController.navigate("userInfoModify_route") },
            { navController.navigate("announcement_route") },
            { navController.navigate("userWithdrawal_route") }
        )
    }

}
