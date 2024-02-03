package com.ssafy.tranvel.presentation.screen.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssafy.tranvel.presentation.screen.history.HistoryScreen


const val historyRoute = "history_route"

fun NavGraphBuilder.historyScreen(navController: NavController) {
    composable(historyRoute) {
        HistoryScreen(
            { navController.navigate("userInfoModify_route") },
            { navController.navigate("announcement_route") },
            { navController.navigate("userWithdrawal_route") }
        )
    }

}