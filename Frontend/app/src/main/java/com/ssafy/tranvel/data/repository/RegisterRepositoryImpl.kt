package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.dto.User
import com.ssafy.tranvel.data.model.request.EmailAuthRequest
import com.ssafy.tranvel.data.model.request.EmailInfoRequest
import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.data.model.request.NicknameRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.model.response.EmailAuthResponse
import com.ssafy.tranvel.data.model.response.EmailInfoResponse
import com.ssafy.tranvel.data.remote.datasource.register.RegisterDataSource
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource
) : RegisterRepository {
    override suspend fun getUser(userRequest: UserRequest): Flow<DataState<DataResponse<TokenDto>>> =
        flow {
            emitAll(registerDataSource.getUser(userRequest))
        }

    override suspend fun getUser(accessToken: String): Flow<DataState<DataResponse<User>>> {
        return registerDataSource.getUser("Bearer $accessToken")
    }

    override suspend fun sendEmailAuth(email: String): Flow<DataState<EmailInfoResponse>> =
        flow {
            emitAll(registerDataSource.sendEmailAuth(email))
        }

    override suspend fun sendEmailAuthNum(emailAuthRequest: EmailAuthRequest): Flow<DataState<EmailAuthResponse>> =
        flow {
            emitAll(registerDataSource.sendEmailAuthNum(emailAuthRequest))
        }

    override suspend fun resetPassword(emailInfoRequest: EmailInfoRequest): Flow<DataState<EmailInfoResponse>> =
        flow {
            emitAll(registerDataSource.resetPassword(emailInfoRequest))
        }

    override suspend fun registerUser(userRequest: UserRequest): Flow<DataState<EmailInfoResponse>> =
        flow {
            emitAll(registerDataSource.registerUser(userRequest))
        }

    override suspend fun duplicateNickName(
        nickname: String,
        email: String
    ): Flow<DataState<EmailInfoResponse>> =
        flow {
            emitAll(registerDataSource.duplicateNickName(nickname, email))
        }
}