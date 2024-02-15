package com.ssafy.tranvel.presentation.screen.splash

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.RegisterRepository
import com.ssafy.tranvel.domain.repository.TokenRepository
import com.ssafy.tranvel.presentation.screen.home.navigation.homeRoute
import com.ssafy.tranvel.presentation.screen.login.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val registerRepository: RegisterRepository
) : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch {
            val token = tokenRepository.getToken()
            Log.d("TAG", "getUser: ${token}")
            if (token != null) {
                getUser(token)
            }
        }
    }

    private suspend fun getUser(accessToken: String) {
        registerRepository.getUser(accessToken).collect {
            when (it) {
                is DataState.Success -> {
                    if (it.data.result) {
                        User.id = it.data.data.id
                        User.email = it.data.data.email
                        User.balance = it.data.data.balance
                        User.profileImage = it.data.data.profileImage
                        User.nickName = it.data.data.nickName
                    }
                    _isReady.emit(true)
                }

                is DataState.Error -> {
                    Log.d("TAG", "getUser: ${it.apiError}")
                    _isReady.emit(true)
                }

                is DataState.Loading -> {
                    _isReady.emit(false)
                }
            }
        }
    }
}