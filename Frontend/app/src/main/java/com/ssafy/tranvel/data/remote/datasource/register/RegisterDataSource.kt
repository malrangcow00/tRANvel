package com.ssafy.tranvel.data.remote.datasource.register

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.request.LoginRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow

interface RegisterDataSource {

    suspend fun getUser(loginRequest: LoginRequest): Flow<DataState<DataResponse<TokenDto>>>

}