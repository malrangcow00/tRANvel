package com.ssafy.tranvel.presentation.screen.announcement.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.presentation.screen.announcement.AnnouncementScreen

const val announcementRoute = "announcement_route"

fun NavGraphBuilder.announcementScreen(navigateToDetailAnnouncement: (AnnouncementDto?) -> Unit) {
    composable(announcementRoute) {
        AnnouncementScreen(
            hiltViewModel(),
        ){
            
        }
    }
}
