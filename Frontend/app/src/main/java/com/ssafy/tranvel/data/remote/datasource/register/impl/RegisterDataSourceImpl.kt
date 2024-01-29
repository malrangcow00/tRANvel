package com.ssafy.tranvel.data.remote.datasource.register.impl

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.request.LoginRequest
import com.ssafy.tranvel.data.model.response.DataResponse
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
        loginRequest: LoginRequest
    ): Flow<DataState<DataResponse<TokenDto>>> =
        getResult { registerService.login(loginRequest = loginRequest) }
}