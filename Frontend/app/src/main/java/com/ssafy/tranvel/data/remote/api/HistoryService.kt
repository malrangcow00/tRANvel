package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.request.HistoryRequest
import com.ssafy.tranvel.data.model.response.DetailHistoryRecordResponse
import com.ssafy.tranvel.data.model.response.AdjustmentHistoryResponse
import com.ssafy.tranvel.data.model.response.AttractionHistoryResponse
import com.ssafy.tranvel.data.model.response.FoodHistoryResponse
import com.ssafy.tranvel.data.model.response.HistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface HistoryService {
    @POST("rooms")
    suspend fun getAllHistories(@Body historyRequest: HistoryRequest): Response<HistoryResponse>

    @FormUrlEncoded
    @POST("rooms/adjustment/getallhistory")
    suspend fun getAdjustmentHistory(@Field("roomId") roomId : Long) : Response<AdjustmentHistoryResponse>

    @FormUrlEncoded
    @POST("rooms/attractiongame/getallhistory")
    suspend fun getAttractiongameHistory(@Field("roomId") roomId : Long) : Response<AttractionHistoryResponse>

    @FormUrlEncoded
    @POST("rooms/foodgame/getallhistory")
    suspend fun getFoodgameHistory(@Field("roomId") roomId : Long) : Response<FoodHistoryResponse>

    @FormUrlEncoded
    @POST("rooms/history")
    suspend fun getDetailHistoryRecords(@Field("roomId") roomId : Long) : Response<DetailHistoryRecordResponse>
}
