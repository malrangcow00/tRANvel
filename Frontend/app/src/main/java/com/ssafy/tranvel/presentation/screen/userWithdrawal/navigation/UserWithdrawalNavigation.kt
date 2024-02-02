package com.ssafy.tranvel.presentation.screen.userWithdrawal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.tranvel.presentation.screen.userInfoModification.UserInfoModifyScreen
import com.ssafy.tranvel.presentation.screen.userWithdrawal.UserWithdrawalScreen

const val userWithdrawalRoute = "userWithdrawal_route"

fun NavGraphBuilder.userWithdrawalScreen(navController: NavController){
    composable(userWithdrawalRoute){
        UserWithdrawalScreen()
    }
}
