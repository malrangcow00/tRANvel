package com.ssafy.tranvel.presentation.screen.history

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ssafy.tranvel.data.model.dto.DetailHistoryDto
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.history.component.HistoryBody
import com.ssafy.tranvel.presentation.screen.history.component.HistoryHeader

private const val TAG = "HistoryScreen_싸피"

@Composable
fun HistoryScreen(
    historyDto: HistoryDto?,
    detailHistoryViewModel: DetailHistoryViewModel,
    detailHistoryRecordViewModel: DetailHistoryRecordViewModel
) {
    Scaffold(
        topBar = { HistoryHeader(historyDto) }
    ) {
        Log.d(TAG, "HistoryScreen: 여기가 계속 재생성되나?")
        HistoryBody(
            it, historyDto, detailHistoryViewModel, detailHistoryRecordViewModel
        )
    }

}
