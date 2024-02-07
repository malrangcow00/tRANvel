package com.ssafy.tranvel.domain.repository

interface TokenRepository {
    fun setToken(token: String, key: String)
    fun getToken():String?
}