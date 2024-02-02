package com.ssafy.tranvel.domain.usecase.register

import com.ssafy.tranvel.data.model.request.EmailInfoRequest
import com.ssafy.tranvel.domain.repository.RegisterRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend fun execute(email: String) = registerRepository.resetPassword(EmailInfoRequest(email))
}