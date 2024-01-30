package com.ssafy.tranvel.domain.usecase.register

import com.ssafy.tranvel.data.model.request.EmailInfoRequest
import com.ssafy.tranvel.data.model.response.EmailInfoResponse
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendEmailAuthUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend fun execute(email:String): Flow<DataState<EmailInfoResponse>> {
        return registerRepository.sendEmailAuth(EmailInfoRequest(email))
    }
}