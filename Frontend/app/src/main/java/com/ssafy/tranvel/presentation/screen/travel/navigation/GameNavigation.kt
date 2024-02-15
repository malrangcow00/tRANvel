package com.ssafy.tranvel.presentation.screen.travel.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.tranvel.presentation.screen.travel.AccountScreen
import com.ssafy.tranvel.presentation.screen.travel.FoodScreen
import com.ssafy.tranvel.presentation.screen.travel.GameScreen
import com.ssafy.tranvel.presentation.screen.travel.component.Screen


fun NavGraphBuilder.gameNavGraph(navController: NavController) {
    navigation(startDestination = Screen.Draw.route!!, route = "game") {
        composable(route = Screen.Draw.route) {
            GameScreen(
                navController,
                hiltViewModel(it),
                hiltViewModel()
            )
        }

        composable(route = Screen.Restaurant.route!!) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.Draw.route)
            }
            FoodScreen(
                hiltViewModel(parentEntry),
                onBackPressed = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Account.route!!) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.Draw.route)
            }
            AccountScreen(
                hiltViewModel(parentEntry)
            ) {
                navController.popBackStack()
            }
        }
    }
}