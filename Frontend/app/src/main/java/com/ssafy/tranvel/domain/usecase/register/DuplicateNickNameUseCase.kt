package com.ssafy.tranvel.domain.usecase.register

import com.ssafy.tranvel.data.model.request.NicknameRequest
import com.ssafy.tranvel.data.model.response.EmailInfoResponse
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DuplicateNickNameUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend fun execute(nickname: String, email: String): Flow<DataState<EmailInfoResponse>> {
        return registerRepository.duplicateNickName(nickname,email)
    }
}