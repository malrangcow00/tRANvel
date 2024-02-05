package com.ssafy.tranvel.domain.viewstate.history

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.AllHistoryResult
import com.ssafy.tranvel.data.model.dto.AllHistoryDto
import com.ssafy.tranvel.domain.viewstate.IViewState
import kotlinx.coroutines.flow.Flow

@Stable
data class HistoryViewState(
    val isLoading: Boolean = false,
    val pagedData: Flow<PagingData<AllHistoryDto>>? = null,
    val data : List<AllHistoryResult>?= null
) : IViewState
