package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.request.EmailAuthRequest
import com.ssafy.tranvel.data.model.request.EmailInfoRequest
import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.data.model.request.NicknameRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.model.response.EmailAuthResponse
import com.ssafy.tranvel.data.model.response.EmailInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("user/signin")
    suspend fun login(@Body userRequest: UserRequest): Response<DataResponse<TokenDto>>

    @POST("email-auth")
    suspend fun sendEmailAuth(@Body emailInfoRequest: EmailInfoRequest): Response<EmailInfoResponse>

    @POST("email-auth/verification")
    suspend fun sendAuthNum(@Body emailAuthRequest: EmailAuthRequest): Response<EmailAuthResponse>

    @POST("user/reset-password")
    suspend fun resetPassword(@Body emailInfoRequest: EmailInfoRequest): Response<EmailInfoResponse>

    @POST("signup")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<EmailInfoResponse>

    @POST("user/duplication")
    suspend fun duplicateNickName(@Body nicknameRequest: NicknameRequest): Response<EmailInfoResponse>
}