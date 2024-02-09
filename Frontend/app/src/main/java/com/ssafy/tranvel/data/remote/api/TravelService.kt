package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.response.DataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}