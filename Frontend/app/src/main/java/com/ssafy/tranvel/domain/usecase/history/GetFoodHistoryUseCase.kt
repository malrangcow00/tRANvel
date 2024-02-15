package com.ssafy.tranvel.domain.usecase.history

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.dto.AdjustmentHistoryDto
import com.ssafy.tranvel.data.model.dto.FoodHistoryDto
import com.ssafy.tranvel.data.model.response.AttractionHistoryResponse
import com.ssafy.tranvel.data.model.response.FoodHistoryResponse
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFoodHistoryUseCase @Inject constructor(
    private val historyRepository : HistoryRepository
){
    suspend fun execute(roomId : Long) :Flow<DataState<FoodHistoryResponse>> {
        return historyRepository.getFoodHistory(roomId)
    }
}
