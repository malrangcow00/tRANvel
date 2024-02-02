package com.ssafy.tranvel.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.tranvel.presentation.screen.announcement.navigation.announcementScreen
import com.ssafy.tranvel.presentation.screen.history.navigation.historyScreen
import com.ssafy.tranvel.presentation.screen.login.LoginScreen
import com.ssafy.tranvel.presentation.screen.register.navigation.registerGraph
import com.ssafy.tranvel.presentation.screen.userInfoModification.navigation.userInfoModifyScreen
import com.ssafy.tranvel.presentation.screen.userWithdrawal.navigation.userWithdrawalScreen

@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(
        modifier = Modifier.background(color = Color.White)
    ){
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login_screen",
            Modifier.padding(innerPadding)
        ){
            announcementScreen { }
            composable(route = "login_screen") {
                LoginScreen() {
                    navController.navigate("register")
                }
            }
            registerGraph(navController)
            historyScreen(navController)
            userInfoModifyScreen(navController)
            userWithdrawalScreen(navController)
        }
    }
}
