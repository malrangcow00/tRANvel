package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.model.response.DetailHistoryRecordResponse
import com.ssafy.tranvel.data.model.response.AdjustmentHistoryResponse
import com.ssafy.tranvel.data.model.response.AttractionHistoryResponse
import com.ssafy.tranvel.data.model.response.FoodHistoryResponse
import com.ssafy.tranvel.data.model.response.HistoryResponse
import com.ssafy.tranvel.data.remote.datasource.history.HistoryRemoteDataSource
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyRemoteDataSource: HistoryRemoteDataSource
) : HistoryRepository {
    override suspend fun getAllHistories(userId: Long): Response<HistoryResponse> =
        historyRemoteDataSource.getAllHistories(userId)

    override suspend fun getAdjustmentHistory(roomId: Long): Flow<DataState<AdjustmentHistoryResponse>> =
        flow{
            historyRemoteDataSource.getAdjustmentHistory(roomId)
        }

    override suspend fun getAttractionHistory(roomId: Long): Flow<DataState<AttractionHistoryResponse>> =
        flow{
            historyRemoteDataSource.getAttractionHistory(roomId)
        }


    override suspend fun getFoodHistory(roomId: Long): Flow<DataState<FoodHistoryResponse>> =
        flow{
            historyRemoteDataSource.getFoodHistory(roomId)
        }

    override suspend fun getDetailHistoryRecord(roomId: Long): Response<DetailHistoryRecordResponse> =
        historyRemoteDataSource.getDetailHistoryRecord(roomId)
}
