package com.ssafy.tranvel.data.remote.datasource.history.impl

import com.ssafy.tranvel.data.model.request.HistoryRequest
import com.ssafy.tranvel.data.model.response.DetailHistoryRecordResponse
import com.ssafy.tranvel.data.model.response.AdjustmentHistoryResponse
import com.ssafy.tranvel.data.model.response.AttractionHistoryResponse
import com.ssafy.tranvel.data.model.response.FoodHistoryResponse
import com.ssafy.tranvel.data.model.response.HistoryResponse
import com.ssafy.tranvel.data.remote.api.HistoryService
import com.ssafy.tranvel.data.remote.datasource.BaseDataSource
import com.ssafy.tranvel.data.remote.datasource.history.HistoryRemoteDataSource
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class HistoryRemoteDataSourceImpl @Inject constructor(
    private val historyService: HistoryService
) : BaseDataSource(), HistoryRemoteDataSource{
    override suspend fun getAllHistories(userId : Long): Response<HistoryResponse> = historyService.getAllHistories(HistoryRequest(userId))

    override suspend fun getAdjustmentHistory(roomId : Long): Flow<DataState<AdjustmentHistoryResponse>> = getResult {
        historyService.getAdjustmentHistory(roomId)
    }

    override suspend fun getAttractionHistory(roomId : Long): Flow<DataState<AttractionHistoryResponse>> = getResult {
        historyService.getAttractiongameHistory(roomId)
    }

    override suspend fun getFoodHistory(roomId : Long): Flow<DataState<FoodHistoryResponse>> = getResult {
        historyService.getFoodgameHistory(roomId)
    }
    override suspend fun getDetailHistoryRecord(roomId: Long): Response<DetailHistoryRecordResponse> = historyService.getDetailHistoryRecords(roomId)
}
