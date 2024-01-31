package com.ssafy.tranvel.presentation.screen.announcement

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.presentation.screen.announcement.component.AnnouncementBody
import com.ssafy.tranvel.presentation.screen.announcement.component.AnnouncementHeader

@Composable
fun AnnouncementScreen(
    viewModel: AnnouncementViewModel,
    navigateToDetail: (AnnouncementDto?) -> Unit
) {
    Scaffold(
        topBar = { AnnouncementHeader() },
    ) { innerPadding ->
        AnnouncementBody(
            innerPadding,
            viewModel, navigateToDetail
        )
    }
}
