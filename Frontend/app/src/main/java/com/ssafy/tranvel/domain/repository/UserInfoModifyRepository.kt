package com.ssafy.tranvel.domain.repository

import com.ssafy.tranvel.data.model.request.UserInfoModifyRequest
import com.ssafy.tranvel.data.model.response.LoadUserInfoResponse
import com.ssafy.tranvel.data.model.response.UserInfoModifyResponse
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow

interface UserInfoModifyRepository {
    suspend fun modifyUserInfo(userInfoModifyRequest: UserInfoModifyRequest) : Flow<DataState<UserInfoModifyResponse>>

    suspend fun loadUserInfo(userId: String) : Flow<DataState<LoadUserInfoResponse>>
}
