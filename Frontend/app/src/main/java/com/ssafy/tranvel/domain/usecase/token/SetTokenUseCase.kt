package com.ssafy.tranvel.domain.usecase.token

import com.ssafy.tranvel.domain.repository.TokenRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    fun execute(token: String, key: String) {
        tokenRepository.setToken(token, key)
    }
}