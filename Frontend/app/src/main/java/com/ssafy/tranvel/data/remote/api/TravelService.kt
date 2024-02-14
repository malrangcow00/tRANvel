package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.dto.AdjustmentGameHistory
import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.dto.UserInfo
import com.ssafy.tranvel.data.model.response.DataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface TravelService {

    @FormUrlEncoded
    @POST("rooms/create")
    suspend fun createRoom(@Field("roomPassword") roomPassword: String): Response<DataResponse<Room<Boolean>>>

    @FormUrlEncoded
    @POST("rooms/enter")
    suspend fun enterRoom(
        @Field("roomCode") roomCode: String,
        @Field("roomPassword") roomPassword: String
    ): Response<DataResponse<Room<Boolean>>>

    @FormUrlEncoded
    @POST("rooms/adjustment/select")
    suspend fun getUserList(
        @Field("roomId") roomId: Long
    ): Response<DataResponse<List<UserInfo>>>

    @Multipart
    @POST("rooms/adjustment/record")
    suspend fun setAdjustmentHistory(
        @Part("adjustmentGameHistoryDto") adjustmentGameHistoryDto: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): Response<DataResponse<Int>>
}