package com.ssafy.tranvel.data.remote.datasource.history.impl

import com.ssafy.tranvel.data.model.response.HistoryResponse
import com.ssafy.tranvel.data.remote.api.HistoryService
import com.ssafy.tranvel.data.remote.datasource.BaseDataSource
import com.ssafy.tranvel.data.remote.datasource.history.HistoryRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class HistoryRemoteDataSourceImpl @Inject constructor(
    private val historyService: HistoryService
) : BaseDataSource(), HistoryRemoteDataSource{
    override suspend fun getAllHistories(): Response<HistoryResponse> = historyService.getAllHistories()
}