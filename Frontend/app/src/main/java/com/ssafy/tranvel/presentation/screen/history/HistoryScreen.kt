package com.ssafy.tranvel.presentation.screen.history

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.history.component.HistoryBody
import com.ssafy.tranvel.presentation.screen.history.component.HistoryHeader

private const val TAG = "HistoryScreen_싸피"

@Composable
fun HistoryScreen(
    historyDto: HistoryDto?,
    detailHistoryViewModel: DetailHistoryViewModel
) {
    Scaffold(
        topBar = { HistoryHeader(historyDto) }
    ) {
        Log.d(TAG, "HistoryScreen: 여기가 계속 재생성되나?")
        if (historyDto != null) {
            detailHistoryViewModel.getAdjustmentHistory(historyDto.roomid)
            detailHistoryViewModel.getAttractionHistory(historyDto.roomid)
            detailHistoryViewModel.getFoodHistory(historyDto.roomid)
            Log.d(TAG, "HistoryScreen: adjustmentlist : ${detailHistoryViewModel.adjustmentList}")
            Log.d(TAG, "HistoryScreen: attractionlist : ${detailHistoryViewModel.attractionList}")
            Log.d(TAG, "HistoryScreen: foodlist : ${detailHistoryViewModel.foodList}")
            HistoryBody(
                it, historyDto.roomid, detailHistoryViewModel
            )
        }
    }

}
