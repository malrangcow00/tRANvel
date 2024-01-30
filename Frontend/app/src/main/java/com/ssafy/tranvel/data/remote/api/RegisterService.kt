package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.request.EmailAuthRequest
import com.ssafy.tranvel.data.model.request.EmailInfoRequest
import com.ssafy.tranvel.data.model.request.LoginRequest
import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.model.response.EmailAuthResponse
import com.ssafy.tranvel.data.model.response.EmailInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegisterService {
    @POST("user/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<DataResponse<TokenDto>>

    @POST("emailauth")
    suspend fun sendEmailAuth(@Body emailInfoRequest: EmailInfoRequest): Response<EmailInfoResponse>

    @POST("emailauth/verification")
    suspend fun sendAuthNum(@Body emailAuthRequest: EmailAuthRequest): Response<EmailAuthResponse>

    @POST("user/reset-password")
    suspend fun resetPassword(@Body emailInfoRequest: EmailInfoRequest): Response<EmailInfoResponse>

    @POST("signup")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<EmailInfoResponse>

    @GET("user/duplication/{nickname}")
    suspend fun duplicateNickName(@Path("nickname") nickname: String): Response<EmailInfoResponse>
}