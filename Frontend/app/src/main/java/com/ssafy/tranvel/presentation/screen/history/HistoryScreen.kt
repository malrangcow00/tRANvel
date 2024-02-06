package com.ssafy.tranvel.presentation.screen.history

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.ssafy.tranvel.presentation.screen.history.component.HistoryBody
import com.ssafy.tranvel.presentation.screen.history.component.HistoryHeader

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    ) {
    Scaffold(
        topBar = { HistoryHeader() }
    ) {
        HistoryBody(
            it,
            viewModel
        )
    }

}