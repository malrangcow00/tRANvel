package com.ssafy.tranvel.presentation.screen.travel.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun GameBody(
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier.padding(innerPadding).fillMaxHeight()
    ) {
        NaverMap(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
        )
    }
}