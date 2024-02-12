package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.model.response.DetailHistoryRecordResponse
import com.ssafy.tranvel.data.model.response.DetailHistoryResponse
import com.ssafy.tranvel.data.model.response.HistoryResponse
import com.ssafy.tranvel.data.remote.datasource.history.HistoryRemoteDataSource
import com.ssafy.tranvel.domain.repository.HistoryRepository
import retrofit2.Response
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyRemoteDataSource: HistoryRemoteDataSource
) : HistoryRepository {
    override suspend fun getAllHistories(userId: Long): Response<HistoryResponse> =
        historyRemoteDataSource.getAllHistories(userId)

    override suspend fun getDetailHistory(roomId: Long): Response<DetailHistoryResponse> =
        historyRemoteDataSource.getDetailHistory(roomId)

    override suspend fun getDetailHistoryRecord(roomId: Long): Response<DetailHistoryRecordResponse> =
        historyRemoteDataSource.getDetailHistoryRecord(roomId)
}
