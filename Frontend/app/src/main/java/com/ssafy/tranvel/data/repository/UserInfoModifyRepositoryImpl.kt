package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.model.request.UserInfoModifyRequest
import com.ssafy.tranvel.data.model.response.LoadUserInfoResponse
import com.ssafy.tranvel.data.model.response.UserInfoModifyResponse
import com.ssafy.tranvel.data.remote.datasource.userInfoModification.UserInfoModifyRemoteDataSource
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.UserInfoModifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserInfoModifyRepositoryImpl @Inject constructor(
    private val userInfoModifyRemoteDataSource: UserInfoModifyRemoteDataSource
) : UserInfoModifyRepository {
    override suspend fun modifyUserInfo(userInfoModifyRequest: UserInfoModifyRequest): Flow<DataState<UserInfoModifyResponse>> =
        flow {
            emitAll(userInfoModifyRemoteDataSource.modifyUserInfo(userInfoModifyRequest))
        }

    override suspend fun loadUserInfo(userId: String): Flow<DataState<LoadUserInfoResponse>> =
        flow {
            emitAll(userInfoModifyRemoteDataSource.loadUserInfo(userId))
        }
}