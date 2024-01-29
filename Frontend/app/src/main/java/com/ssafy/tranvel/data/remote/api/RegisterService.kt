package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.request.LoginRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("user/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<DataResponse<TokenDto>>

}