package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.request.HistoryRequest
import com.ssafy.tranvel.data.model.response.HistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HistoryService {
    @POST("rooms")
    suspend fun getAllHistories(@Body historyRequest: HistoryRequest): Response<HistoryResponse>
}
