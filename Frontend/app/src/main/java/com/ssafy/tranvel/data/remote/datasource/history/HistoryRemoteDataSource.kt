package com.ssafy.tranvel.data.remote.datasource.history

import com.ssafy.tranvel.data.model.response.DetailHistoryRecordResponse
import com.ssafy.tranvel.data.model.response.AdjustmentHistoryResponse
import com.ssafy.tranvel.data.model.response.AttractionHistoryResponse
import com.ssafy.tranvel.data.model.response.FoodHistoryResponse
import com.ssafy.tranvel.data.model.response.HistoryResponse
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface HistoryRemoteDataSource {
    suspend fun getAllHistories(userId : Long) : Response<HistoryResponse>

    suspend fun getAdjustmentHistory(roomId : Long) : Flow<DataState<AdjustmentHistoryResponse>>

    suspend fun getAttractionHistory(roomId : Long) : Flow<DataState<AttractionHistoryResponse>>

    suspend fun getFoodHistory(roomId : Long) : Flow<DataState<FoodHistoryResponse>>

    suspend fun getDetailHistoryRecord(roomId: Long) : Response<DetailHistoryRecordResponse>
}
