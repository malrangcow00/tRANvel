package com.ssafy.tranvel.presentation.screen.travel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.ssafy.tranvel.presentation.screen.travel.TravelViewModel
import com.ssafy.tranvel.presentation.ui.theme.PrimaryColor

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun GameBody(
    travelViewModel: TravelViewModel,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        NaverMap(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        )
//        HomeHistoryBody(historyViewModel = , navigateToHistory = )
    }
}