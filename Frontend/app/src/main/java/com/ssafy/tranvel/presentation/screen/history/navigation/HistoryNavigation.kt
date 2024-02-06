package com.ssafy.tranvel.presentation.screen.history.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssafy.tranvel.presentation.screen.history.HistoryScreen

const val historyRoute = "history_route"

fun NavController.navigateHistory() {
    this.navigate(historyRoute)
}

fun NavGraphBuilder.historyScreen(navController: NavController){
    composable(historyRoute){
        HistoryScreen(hiltViewModel())
    }
}