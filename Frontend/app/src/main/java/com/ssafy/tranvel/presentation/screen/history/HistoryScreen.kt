package com.ssafy.tranvel.presentation.screen.history

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.history.component.HistoryBody
import com.ssafy.tranvel.presentation.screen.history.component.HistoryHeader

private const val TAG = "HistoryScreen_싸피"

@Composable
fun HistoryScreen(
    dto: HistoryDto?
) {
    Scaffold(
        topBar = { HistoryHeader(dto) }
    ) {
        HistoryBody(
            it, dto
        )
    }

}
