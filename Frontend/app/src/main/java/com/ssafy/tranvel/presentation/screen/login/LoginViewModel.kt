package com.ssafy.tranvel.presentation.screen.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.usecase.register.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    val id: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")

    private val _uiState: MutableStateFlow<String> = MutableStateFlow("")
    val uiState: StateFlow<String> = _uiState

    private val _currentState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentState: StateFlow<Boolean> = _currentState

    private val _visibilityPassword: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val visibilityPassword: StateFlow<Boolean> = _visibilityPassword

    fun checkId(): Boolean {
        val validation = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+\$"
        if (id.value == "") {
            return true
        }
        return Pattern.matches(validation, id.value)
    }

    fun checkPassword(): Boolean {
        if (password.value == "") {
            return true
        }
        return password.value.length in 8 .. 15
    }

    fun changePasswordVisibility() {
        _visibilityPassword.value = !_visibilityPassword.value
    }

    fun loginUser() {
        viewModelScope.launch {
            _currentState.emit(true)
            getUserUseCase.execute(UserRequest(email = id.value, password = password.value, balance = 0, profileImage = "", nickName = "asdf")).collect{
                when (it) {
                    is DataState.Success -> {
                        _uiState.emit("SUCCESS")
                        _currentState.emit(false)
                    }
                    is DataState.Error -> {
                        _uiState.emit("ERROR")
                        _currentState.emit(false)
                    }
                    is DataState.Loading -> {
                        _uiState.emit("LOADING")
                        _currentState.emit(true)
                    }
                }
            }
        }
    }
}