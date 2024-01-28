package com.ssafy.tranvel.domain.usecase.register

import com.ssafy.tranvel.data.model.TokenDto
import com.ssafy.tranvel.data.model.request.LoginRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUserUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend fun execute(loginRequest: LoginRequest): Flow<DataState<DataResponse<TokenDto>>> =
        registerRepository.getUser(loginRequest)
}