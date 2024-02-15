package com.ssafy.tranvel.domain.viewstate.history

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.AdjustmentHistoryResult
import com.ssafy.tranvel.data.model.AttractionHistoryResult
import com.ssafy.tranvel.data.model.FoodHistoryResult
import com.ssafy.tranvel.data.model.dto.AdjustmentHistoryDto
import com.ssafy.tranvel.data.model.dto.AttractionHistoryDto
import com.ssafy.tranvel.data.model.dto.FoodHistoryDto
import com.ssafy.tranvel.domain.viewstate.IViewState
import kotlinx.coroutines.flow.Flow

@Stable
data class FoodHistoryViewState(
    val isLoading: Boolean = false,
    val pagedData: Flow<PagingData<FoodHistoryDto>>? = null,
    val data: List<FoodHistoryResult>? = null
) : IViewState
