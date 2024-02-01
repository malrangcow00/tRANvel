package com.ssafy.tranvel.data.remote.datasource.userInfoModification.impl

import com.ssafy.tranvel.data.model.request.UserInfoModifyRequest
import com.ssafy.tranvel.data.model.response.LoadUserInfoResponse
import com.ssafy.tranvel.data.model.response.UserInfoModifyResponse
import com.ssafy.tranvel.data.remote.api.UserInfoModifyService
import com.ssafy.tranvel.data.remote.datasource.BaseDataSource
import com.ssafy.tranvel.data.remote.datasource.userInfoModification.UserInfoModifyRemoteDataSource
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.UserInfoModifyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInfoModifyRemoteDataSourceImpl @Inject constructor(
    private val userInfoModifyService: UserInfoModifyService
) : BaseDataSource(), UserInfoModifyRemoteDataSource {
    override suspend fun modifyUserInfo(userInfoModifyRequest: UserInfoModifyRequest): Flow<DataState<UserInfoModifyResponse>> =
        getResult {
            userInfoModifyService.modifyUserInfo(userInfoModifyRequest)
        }

    override suspend fun loadUserInfo(userId: String): Flow<DataState<LoadUserInfoResponse>> =
        getResult {
            userInfoModifyService.loadUserInfo(userId)
        }
}