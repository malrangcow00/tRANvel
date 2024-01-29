package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.request.LoginRequest
import com.ssafy.tranvel.data.model.response.DataResponse
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
    override suspend fun getUser(loginRequest: LoginRequest): Flow<DataState<DataResponse<TokenDto>>> =
        flow {
            emitAll(registerDataSource.getUser(loginRequest))
        }

}