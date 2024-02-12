package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.request.DetailHistoryRequest
import com.ssafy.tranvel.data.model.request.HistoryRequest
import com.ssafy.tranvel.data.model.response.DetailHistoryRecordResponse
import com.ssafy.tranvel.data.model.response.DetailHistoryResponse
import com.ssafy.tranvel.data.model.response.HistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HistoryService {
    @POST("rooms")
    suspend fun getAllHistories(@Body historyRequest: HistoryRequest): Response<HistoryResponse>

    @POST("rooms/adjustment/getallhistory")
    suspend fun getDetailHistories(@Query("roomId") roomId : Long) : Response<DetailHistoryResponse>

    @POST("rooms/history")
    suspend fun getDetailHistoryRecords(@Query("roomId") roomId : Long) : Response<DetailHistoryRecordResponse>
}
