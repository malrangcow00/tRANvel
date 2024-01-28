package com.ssafy.tranvel.domain.repository

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.request.LoginRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun getUser(loginRequest: LoginRequest): Flow<DataState<DataResponse<TokenDto>>>
}