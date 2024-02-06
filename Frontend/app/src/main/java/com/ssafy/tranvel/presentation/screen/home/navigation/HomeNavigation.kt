package com.ssafy.tranvel.presentation.screen.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.announcement.navigation.navigateAnnouncement
import com.ssafy.tranvel.presentation.screen.home.HomeScreen


const val homeRoute = "home_route"

fun NavController.navigateHome(
    navOptions: NavOptions? = null
) {
    this.navigate(homeRoute)
}

fun NavGraphBuilder.homeScreen(navController: NavController, navigateToDetailHistory: (HistoryDto?)->Unit) {
    composable(homeRoute) {
        HomeScreen(
            hiltViewModel(),
            hiltViewModel(),
            { navController.navigate("userInfoModify_route") },
            { navController.navigateAnnouncement() },
            { navController.navigate("userWithdrawal_route") },
            { navController.navigate("game")},
            { navController.navigate("game") },
            { navigateToDetailHistory.invoke(it) }
        )
    }
}
