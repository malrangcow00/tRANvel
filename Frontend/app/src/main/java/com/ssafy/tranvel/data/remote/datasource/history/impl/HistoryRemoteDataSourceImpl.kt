package com.ssafy.tranvel.data.remote.datasource.history.impl

import com.ssafy.tranvel.data.model.request.DetailHistoryRequest
import com.ssafy.tranvel.data.model.request.HistoryRequest
import com.ssafy.tranvel.data.model.response.DetailHistoryRecordResponse
import com.ssafy.tranvel.data.model.response.DetailHistoryResponse
import com.ssafy.tranvel.data.model.response.HistoryResponse
import com.ssafy.tranvel.data.remote.api.HistoryService
import com.ssafy.tranvel.data.remote.datasource.BaseDataSource
import com.ssafy.tranvel.data.remote.datasource.history.HistoryRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class HistoryRemoteDataSourceImpl @Inject constructor(
    private val historyService: HistoryService
) : BaseDataSource(), HistoryRemoteDataSource{
    override suspend fun getAllHistories(userId : Long): Response<HistoryResponse> = historyService.getAllHistories(HistoryRequest(userId))
    override suspend fun getDetailHistory(roomId : Long): Response<DetailHistoryResponse> = historyService.getDetailHistories(roomId)
    override suspend fun getDetailHistoryRecord(roomId: Long): Response<DetailHistoryRecordResponse> = historyService.getDetailHistoryRecords(roomId)
}
