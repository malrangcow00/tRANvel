package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.response.DataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TravelService {

    @POST("rooms/create")
    suspend fun createRoom(@Body room: Room): Response<DataResponse<Room>>
}