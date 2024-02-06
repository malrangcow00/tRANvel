package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.local.PreferenceDataSource
import com.ssafy.tranvel.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
) : TokenRepository {
    override fun setToken(token: String, key: String) {
        preferenceDataSource.putString(key, token)
    }

    override fun getToken(): String? {
        return preferenceDataSource.getString("access_token")
    }
}