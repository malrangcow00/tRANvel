package com.ssafy.tranvel.domain.viewstate.history

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.DetailHistoryResult
import com.ssafy.tranvel.data.model.dto.DetailHistoryDto
import com.ssafy.tranvel.domain.viewstate.IViewState
import kotlinx.coroutines.flow.Flow

@Stable
data class DetailHistoryViewState(
    val isLoading: Boolean = false,
    val pagedData: Flow<PagingData<DetailHistoryDto>>? = null,
    val data: List<DetailHistoryResult>? = null
) : IViewState
