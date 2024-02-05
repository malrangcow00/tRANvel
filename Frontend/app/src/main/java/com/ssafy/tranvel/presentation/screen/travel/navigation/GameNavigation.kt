package com.ssafy.tranvel.presentation.screen.travel.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.tranvel.presentation.screen.travel.GameScreen

fun NavGraphBuilder.gameNavGraph(navController: NavController) {
    navigation(startDestination = "attraction_game_screen", route = "game") {
        composable(route = "attraction_game_screen") {
            GameScreen(
            )
        }
    }
}