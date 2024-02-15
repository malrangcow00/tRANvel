package com.ssafy.tranvel.presentation.screen.userInfoModification.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.tranvel.presentation.screen.home.navigation.homeRoute
import com.ssafy.tranvel.presentation.screen.userInfoModification.UserInfoModifyScreen

const val userInfoModifyNavigationRoute = "userInfoModify_route"

//fun NavController.navigateUserInfoModify(
//    navOptions: NavOptions? = null
//) {
//    this.navigate(homeRoute)
//}
//
//fun NavGraphBuilder.userInfoModifyScreen(navController: NavController){
//    composable(userInfoModifyNavigationRoute){
//        UserInfoModifyScreen(
//            hiltViewModel(),onCancelButtonClicked = {
//                it.viewModelStore.clear()
//                navController.navigate("home_route") {
//                    popUpTo("userInfoModify_route") { inclusive = true }
//                }
//            },
//            onSaveButtonClicked = {
//                it.viewModelStore.clear()
//                navController.navigate("home_route") {
//                    popUpTo("userInfoModify_route") { inclusive = true }
//                }
//            }
//        )
//    }
//}
