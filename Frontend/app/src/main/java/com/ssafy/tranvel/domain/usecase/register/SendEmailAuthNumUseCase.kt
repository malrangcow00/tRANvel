package com.ssafy.tranvel.domain.usecase.register

import com.ssafy.tranvel.data.model.request.EmailAuthRequest
import com.ssafy.tranvel.data.model.response.EmailAuthResponse
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendEmailAuthNumUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend fun execute(verificationCode: String, email: String): Flow<DataState<EmailAuthResponse>> {
        return registerRepository.sendEmailAuthNum(EmailAuthRequest(verificationCode, email))
    }
}