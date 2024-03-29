package com.ssafy.tranvel.presentation.screen.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.announcement.navigation.navigateAnnouncement
import com.ssafy.tranvel.presentation.screen.history.navigation.navigateHistory
import com.ssafy.tranvel.presentation.screen.home.HomeScreen


const val homeRoute = "home_route"

fun NavController.navigateHome(
    navOptions: NavOptions? = null
) {
    this.navigate(homeRoute)
}

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(homeRoute) {
        HomeScreen(
            hiltViewModel(),
            navController,
            hiltViewModel(),
            { navController.navigate("userInfoModify_route") },
            { navController.navigateAnnouncement() },
            { navController.navigate("userWithdrawal_route") },
            {
                navController.navigate("game") {
                    it.viewModelStore.clear()
                    popUpTo(homeRoute) { inclusive = true }
                }
            }
        )
    }
}
