package com.ssafy.tranvel.domain.repository

import com.ssafy.tranvel.data.model.request.EmailAuthRequest
import com.ssafy.tranvel.data.model.request.EmailInfoRequest
import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.data.model.request.NicknameRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.model.response.EmailAuthResponse
import com.ssafy.tranvel.data.model.response.EmailInfoResponse
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun getUser(userRequest: UserRequest): Flow<DataState<DataResponse<Int>>>
    suspend fun sendEmailAuth(emailInfoRequest: EmailInfoRequest): Flow<DataState<EmailInfoResponse>>
    suspend fun sendEmailAuthNum(emailAuthRequest: EmailAuthRequest): Flow<DataState<EmailAuthResponse>>
    suspend fun resetPassword(emailInfoRequest: EmailInfoRequest): Flow<DataState<EmailInfoResponse>>

    suspend fun registerUser(userRequest: UserRequest): Flow<DataState<EmailInfoResponse>>
    suspend fun duplicateNickName(nicknameRequest: NicknameRequest): Flow<DataState<EmailInfoResponse>>
}