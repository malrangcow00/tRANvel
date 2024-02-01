package com.ssafy.tranvel.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.tranvel.presentation.screen.announcement.navigation.announcementNavigationRoute
import com.ssafy.tranvel.presentation.screen.announcement.navigation.announcementScreen
import com.ssafy.tranvel.presentation.screen.announcement.navigation.navigateAnnouncementDetail

@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(
    ){
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = announcementNavigationRoute,
            Modifier.padding(innerPadding)
        ){
            announcementScreen { navController.navigateAnnouncementDetail()}
//            announcementDetailScreen { navController.navigateAnnou }
        }
    }
}
