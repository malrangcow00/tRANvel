package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.dto.User
import com.ssafy.tranvel.data.model.request.EmailAuthRequest
import com.ssafy.tranvel.data.model.request.EmailInfoRequest
import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.data.model.request.NicknameRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.model.response.EmailAuthResponse
import com.ssafy.tranvel.data.model.response.EmailInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RegisterService {
    @POST("user/signin")
    suspend fun login(@Body userRequest: UserRequest): Response<DataResponse<TokenDto>>

    @FormUrlEncoded
    @POST("email-auth")
    suspend fun sendEmailAuth(@Field("email") email: String): Response<EmailInfoResponse>

    @POST("email-auth/verification")
    suspend fun sendAuthNum(@Body emailAuthRequest: EmailAuthRequest): Response<EmailAuthResponse>

    @POST("user/reset-password")
    suspend fun resetPassword(@Body emailInfoRequest: EmailInfoRequest): Response<EmailInfoResponse>

    @POST("signup")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<EmailInfoResponse>

    @FormUrlEncoded
    @POST("user/duplication")
    suspend fun duplicateNickName(
        @Field("email") email: String,
        @Field("nickName") nickName: String
    ): Response<EmailInfoResponse>

    @GET("user/auth/profile")
    suspend fun getUser(@Header("Access-token") accessToken: String): Response<DataResponse<User>>

}