package com.ssafy.tranvel.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.tranvel.presentation.screen.announcement.navigation.announcementScreen
import com.ssafy.tranvel.presentation.screen.announcement.navigation.navigateAnnouncementDetail
import com.ssafy.tranvel.presentation.screen.found.FoundPasswordScreen
import com.ssafy.tranvel.presentation.screen.home.HomeScreen
import com.ssafy.tranvel.presentation.screen.login.LoginScreen
import com.ssafy.tranvel.presentation.screen.register.navigation.registerGraph

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login_screen",
            Modifier.padding(innerPadding)
        ) {
            announcementScreen { navController.navigateAnnouncementDetail() }
//            announcementDetailScreen { navController.navigateAnnou }
            composable(route = "login_screen") {
                LoginScreen(
                    loginViewModel = hiltViewModel(),
                    onNavigateToRegister = {
                        navController.navigate("register")
                    },
                    onNavigateToFound = {
                        navController.navigate("found_screen")
                    },
                    onNavigateToHome = {
                        navController.navigate("home_screen") {
                        }
                    }
                )
            }

            composable(route = "found_screen") {
                FoundPasswordScreen() {
                    navController.navigate("login_screen")
                }
            }

            composable(route = "home_screen") {
                HomeScreen()
            }
            registerGraph(navController)
        }
    }
}
