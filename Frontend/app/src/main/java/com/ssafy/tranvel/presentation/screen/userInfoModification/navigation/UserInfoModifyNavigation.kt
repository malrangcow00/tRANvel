package com.ssafy.tranvel.presentation.screen.userInfoModification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.tranvel.presentation.screen.userInfoModification.UserInfoModifyScreen

const val userInfoModifyNavigationRoute = "userInfoModify_route"

fun NavController.navigateUserInfoModify(
    navOptions: NavOptions? = null
){
    this.navigate(userInfoModifyNavigationRoute)
}

fun NavGraphBuilder.userInfoModifyScreen(){
    composable(userInfoModifyNavigationRoute){
        UserInfoModifyScreen(
//            hiltViewModel()
        )
    }
}
