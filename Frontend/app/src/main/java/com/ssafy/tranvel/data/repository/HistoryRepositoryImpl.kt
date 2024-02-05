package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.model.response.HistoryResponse
import com.ssafy.tranvel.data.remote.datasource.history.HistoryRemoteDataSource
import com.ssafy.tranvel.domain.repository.HistoryRepository
import retrofit2.Response
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyRemoteDataSource: HistoryRemoteDataSource
) : HistoryRepository {
    override suspend fun getAllHistories(): Response<HistoryResponse> =
        historyRemoteDataSource.getAllHistories()
}