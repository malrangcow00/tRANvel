package com.ssafy.tranvel.presentation.screen.travel.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.tranvel.presentation.screen.home.navigation.homeRoute
import com.ssafy.tranvel.presentation.screen.travel.FoodScreen
import com.ssafy.tranvel.presentation.screen.travel.GameScreen
import com.ssafy.tranvel.presentation.screen.travel.component.Screen


fun NavGraphBuilder.gameNavGraph(navController: NavController) {
    navigation(startDestination = Screen.Draw.route!!, route = "game") {
        composable(route = Screen.Draw.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(homeRoute)
            }
            GameScreen(
                hiltViewModel(parentEntry),
                navController
            )
        }

        composable(route = Screen.Adjust.route!!) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(homeRoute)
            }

        }

        composable(route = Screen.Adjust.route!!) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(homeRoute)
            }
            FoodScreen(navController)
        }
    }
}