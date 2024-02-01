package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.request.UserInfoModifyRequest
import com.ssafy.tranvel.data.model.response.LoadUserInfoResponse
import com.ssafy.tranvel.data.model.response.UserInfoModifyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserInfoModifyService {

    //수정된 회원 정보 업데이트 요청
    @PUT("user/auth/profile")
    suspend fun modifyUserInfo(@Body userInfoModifyRequest: UserInfoModifyRequest) : Response<UserInfoModifyResponse>

    //수정 전 회원 정보 요청
    @GET("user/auth/{user-id}/profile")
    suspend fun loadUserInfo(@Path("userId") userId : String) : Response<LoadUserInfoResponse>


}