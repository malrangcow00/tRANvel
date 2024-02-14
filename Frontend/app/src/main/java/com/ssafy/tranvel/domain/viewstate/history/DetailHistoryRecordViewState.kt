package com.ssafy.tranvel.domain.viewstate.history

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.DetailHistoryRecordResult
import com.ssafy.tranvel.data.model.dto.DetailHistoryRecordDto
import com.ssafy.tranvel.domain.viewstate.IViewState
import kotlinx.coroutines.flow.Flow

@Stable
data class DetailHistoryRecordViewState(
    val isLoading: Boolean = false,
    val pagedData: Flow<PagingData<DetailHistoryRecordDto>>? = null,
    val data: List<DetailHistoryRecordResult>? = null
) : IViewState
