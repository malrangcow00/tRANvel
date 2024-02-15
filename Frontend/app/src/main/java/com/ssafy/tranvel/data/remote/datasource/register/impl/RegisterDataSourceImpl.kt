package com.ssafy.tranvel.data.remote.datasource.register.impl

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.dto.User
import com.ssafy.tranvel.data.model.request.EmailAuthRequest
import com.ssafy.tranvel.data.model.request.EmailInfoRequest
import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.model.response.EmailAuthResponse
import com.ssafy.tranvel.data.model.response.EmailInfoResponse
import com.ssafy.tranvel.data.remote.api.RegisterService
import com.ssafy.tranvel.data.remote.datasource.BaseDataSource
import com.ssafy.tranvel.data.remote.datasource.register.RegisterDataSource
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterDataSourceImpl @Inject constructor(
    private val registerService: RegisterService
) : RegisterDataSource, BaseDataSource() {

    override suspend fun getUser(
        userRequest: UserRequest
    ): Flow<DataState<DataResponse<TokenDto>>> =
        getResult { registerService.login(userRequest) }

    override suspend fun getUser(accessToken: String): Flow<DataState<DataResponse<User>>> {
        return getResult { registerService.getUser(accessToken) }
    }

    override suspend fun sendEmailAuth(
        email: String
    ): Flow<DataState<EmailInfoResponse>> =
        getResult { registerService.sendEmailAuth(email) }

    override suspend fun sendEmailAuthNum(emailAuthRequest: EmailAuthRequest): Flow<DataState<EmailAuthResponse>> =
        getResult { registerService.sendAuthNum(emailAuthRequest) }

    override suspend fun resetPassword(emailInfoRequest: EmailInfoRequest): Flow<DataState<EmailInfoResponse>> =
        getResult { registerService.resetPassword(emailInfoRequest) }

    override suspend fun registerUser(userRequest: UserRequest): Flow<DataState<EmailInfoResponse>> =
        getResult { registerService.registerUser(userRequest) }

    override suspend fun duplicateNickName(
        nickname: String,
        email: String
    ): Flow<DataState<EmailInfoResponse>> =
        getResult { registerService.duplicateNickName(email, nickname) }

}