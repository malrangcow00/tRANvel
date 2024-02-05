package com.ssafy.tranvel.domain.viewstate.history

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.HistoryResult
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.domain.viewstate.IViewState
import kotlinx.coroutines.flow.Flow

@Stable
data class HistoryViewState(
    val isLoading: Boolean = false,
    val pagedData: Flow<PagingData<HistoryDto>>? = null,
    val data : List<HistoryResult>?= null
) : IViewState
