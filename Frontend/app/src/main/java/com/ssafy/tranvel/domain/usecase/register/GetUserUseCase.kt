package com.ssafy.tranvel.domain.usecase.register

import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUserUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend fun execute(userRequest: UserRequest): Flow<DataState<DataResponse<Int>>> =
        registerRepository.getUser(userRequest)
}
