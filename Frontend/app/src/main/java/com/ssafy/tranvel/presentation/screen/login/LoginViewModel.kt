package com.ssafy.tranvel.presentation.screen.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.data.model.request.LoginRequest
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.usecase.register.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    val id: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")

    private val _uiState: MutableStateFlow<String> = MutableStateFlow("")
    val uiState: StateFlow<String> = _uiState
    fun loginUser() {
        viewModelScope.launch {
            getUserUseCase.execute(LoginRequest(id.value, password.value)).collect{
                when (it) {
                    is DataState.Success -> {
                        _uiState.emit("SUCCESS")
                    }
                    is DataState.Error -> {
                        _uiState.emit("ERROR")
                    }
                    is DataState.Loading -> {
                        _uiState.emit("LOADING")
                    }
                }
            }
        }
    }
}