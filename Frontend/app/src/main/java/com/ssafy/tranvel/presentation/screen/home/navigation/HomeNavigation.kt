package com.ssafy.tranvel.presentation.screen.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssafy.tranvel.presentation.screen.announcement.navigation.navigateAnnouncement
import com.ssafy.tranvel.presentation.screen.home.HomeScreen


const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(homeRoute) {
        HomeScreen(
            hiltViewModel(),
            { navController.navigate("userInfoModify_route") },
            { navController.navigateAnnouncement() },
            { navController.navigate("userWithdrawal_route") },
            {}, {}
        )
    }

}
