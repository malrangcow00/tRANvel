package com.ssafy.tranvel.presentation.screen.history.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.history.HistoryScreen

const val historyRoute = "history_route"

private const val TAG = "HistoryNavigation_싸피"
fun NavController.navigateHistory(
    dto: HistoryDto?,
) {
    Log.d(TAG, "navigateHistory: ${dto?.roomName.orEmpty()}")
    this.navigate(historyRoute)
}

fun NavGraphBuilder.historyScreen(navController: NavController) {
    composable(historyRoute) {
        HistoryScreen(hiltViewModel())
    }
}
