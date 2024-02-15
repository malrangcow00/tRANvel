package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.request.HistoryRequest
import com.ssafy.tranvel.data.model.response.DetailHistoryRecordResponse
import com.ssafy.tranvel.data.model.response.AdjustmentHistoryResponse
import com.ssafy.tranvel.data.model.response.AttractionHistoryResponse
import com.ssafy.tranvel.data.model.response.FoodHistoryResponse
import com.ssafy.tranvel.data.model.response.HistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface HistoryService {
    @POST("rooms")
    suspend fun getAllHistories(@Body historyRequest: HistoryRequest): Response<HistoryResponse>

    @POST("rooms/adjustment/getallhistory")
    suspend fun getAdjustmentHistory(@Query("roomId") roomId : Long) : Response<AdjustmentHistoryResponse>

    @POST("rooms/attractiongame/getallhistory")
    suspend fun getAttractiongameHistory(@Query("roomId") roomId : Long) : Response<AttractionHistoryResponse>

    @POST("rooms/foodgame/getallhistory")
    suspend fun getFoodgameHistory(@Query("roomId") roomId : Long) : Response<FoodHistoryResponse>

    @POST("rooms/history")
    suspend fun getDetailHistoryRecords(@Query("roomId") roomId : Long) : Response<DetailHistoryRecordResponse>
}
