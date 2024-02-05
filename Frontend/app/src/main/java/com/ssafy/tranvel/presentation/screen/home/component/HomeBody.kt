package com.ssafy.tranvel.presentation.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.history.HistoryViewModel
import com.ssafy.tranvel.presentation.screen.travel.TravelViewModel

@Composable
fun HomeBody(
    innerPadding: PaddingValues,
    travelViewModel: TravelViewModel,
    historyViewModel: HistoryViewModel,
    onEnterButtonClicked: () -> Unit,
    onCreateButtonClicked: () -> Unit,
    onHistoryClicked: (HistoryDto?) -> Unit
) {
    Column(
        modifier = Modifier.padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        HomeRoomBody(roomViewModel, onEnterButtonClicked, onCreateButtonClicked)
        HomeRoomBody(travelViewModel, onEnterButtonClicked, onCreateButtonClicked)
        HomeHistoryBody(historyViewModel, onHistoryClicked)
    }
}
