package com.ssafy.tranvel.presentation.screen.userInfoModification.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.tranvel.presentation.screen.userInfoModification.UserInfoModifyScreen

const val userInfoModifyNavigationRoute = "userInfoModify_route"

fun NavGraphBuilder.userInfoModifyScreen(navController: NavController){
    composable(userInfoModifyNavigationRoute){
        UserInfoModifyScreen(
            hiltViewModel(),
        )
    }
}
