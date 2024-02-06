package com.ssafy.tranvel.presentation.screen.history.component

import android.util.Log
import androidx.compose.runtime.Composable
import com.ssafy.tranvel.data.model.dto.HistoryDto

private const val TAG = "HistoryHeader_싸피"
@Composable
fun HistoryHeader(
    dto: HistoryDto?
){
    Log.d(TAG, "HistoryHeader: ${dto?.roomName.orEmpty()}")
    HistoryTitle(dto)
    HistoryHeaderImages()
}
