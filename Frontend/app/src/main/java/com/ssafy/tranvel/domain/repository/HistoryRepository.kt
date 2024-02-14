package com.ssafy.tranvel.domain.repository

import com.ssafy.tranvel.data.model.response.DetailHistoryRecordResponse
import com.ssafy.tranvel.data.model.response.DetailHistoryResponse
import com.ssafy.tranvel.data.model.response.HistoryResponse
import retrofit2.Response

interface HistoryRepository {
    suspend fun getAllHistories(userId : Long) : Response<HistoryResponse>

    suspend fun getDetailHistory(roomId : Long) : Response<DetailHistoryResponse>

    suspend fun getDetailHistoryRecord(roomId: Long) : Response<DetailHistoryRecordResponse>
}
