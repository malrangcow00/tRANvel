package com.ssafy.tranvel.presentation.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ssafy.tranvel.presentation.screen.home.HomeViewModel

@Composable
fun HomeBody(
    innerPadding: PaddingValues,
    homeViewModel: HomeViewModel,
    onEnterButtonClicked: () -> Unit,
    onCreateButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeRoomBody(homeViewModel, onEnterButtonClicked, onCreateButtonClicked)
        HomeHistoryBody(homeViewModel)
    }
}
