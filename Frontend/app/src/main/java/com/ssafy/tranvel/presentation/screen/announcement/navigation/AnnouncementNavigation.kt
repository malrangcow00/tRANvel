package com.ssafy.tranvel.presentation.screen.announcement.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.presentation.screen.announcement.AnnouncementScreen

const val announcementNavigationRoute = "announcement_route"

fun NavController.navigateAnnouncementDetail(
    navOptions: NavOptions? = null
){
    this.navigate(announcementNavigationRoute)
}

fun NavGraphBuilder.announcementScreen(navigateToDetail: (AnnouncementDto?) -> Unit){
    composable(announcementNavigationRoute){
        AnnouncementScreen(
            hiltViewModel(),
            navigateToDetail = {
                navigateToDetail.invoke(it)
            }
        )
    }
}
