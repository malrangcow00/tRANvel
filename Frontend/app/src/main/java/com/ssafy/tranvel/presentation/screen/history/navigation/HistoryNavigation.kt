package com.ssafy.tranvel.presentation.screen.history.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.tranvel.data.model.dto.DetailHistoryDto
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.history.DetailHistoryViewModel
import com.ssafy.tranvel.presentation.screen.history.HistoryScreen

const val historyRoute = "history_route"

private const val TAG = "HistoryNavigation_싸피"
fun NavController.navigateHistory(
    historyDto: HistoryDto?
) {
    Log.d(TAG, "navigateHistory: roomName? : ${historyDto?.roomName.orEmpty()}")
    this.currentBackStackEntry?.savedStateHandle?.set("historyDto",historyDto)
    this.navigate(historyRoute)
}

fun NavGraphBuilder.historyScreen(navController: NavController) {
    val historyObject : HistoryDto?=navController.previousBackStackEntry?.savedStateHandle?.get("historyDto")
    Log.d(TAG, "historyScreen: 왜 자꾸 recompose 하는데")

    composable(historyRoute) {
        Log.d(TAG, "historyScreen: 너가 문제냐")
        HistoryScreen(historyObject, hiltViewModel(), hiltViewModel())
    }
}
