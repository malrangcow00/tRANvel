package com.ssafy.tranvel.presentation.screen.register

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.usecase.register.DuplicateNickNameUseCase
import com.ssafy.tranvel.domain.usecase.register.RegisterUserUseCase
import com.ssafy.tranvel.domain.usecase.register.SendEmailAuthNumUseCase
import com.ssafy.tranvel.domain.usecase.register.SendEmailAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val duplicateNickNameUseCase: DuplicateNickNameUseCase,
    private val sendEmailAuthNumUseCase: SendEmailAuthNumUseCase,
    private val sendEmailAuthUseCase: SendEmailAuthUseCase
) : ViewModel() {

    private val _bitmap: MutableStateFlow<Bitmap?> = MutableStateFlow(null)
    val bitmap: StateFlow<Bitmap?> = _bitmap

    val id: MutableState<String> = mutableStateOf("")
    val nickname: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")
    val verification: MutableState<String> = mutableStateOf("")
    val verificationCode: MutableState<String> = mutableStateOf("")

    private val _currentState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentState: StateFlow<Boolean> = _currentState
    private val _authButtonState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val authButtonState: StateFlow<Boolean> = _authButtonState
    private val _resetButtonState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val resetButtonState: StateFlow<Boolean> = _resetButtonState
    fun setBitmap(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

    fun checkId(): Boolean {
        val validation = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+\$"
        if (id.value == "") {
            return true
        }
        return Pattern.matches(validation, id.value)
    }

    fun checkPassword(): Boolean {
        val validation = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""
        val pattern = Pattern.compile(validation)
        if (password.value == "") {
            return true
        }

        return pattern.matcher(password.value).matches()
    }

    fun matchPassword(): Boolean {
        if (verification.value == "") {
            return true
        }
        return verification.value == password.value
    }

    fun registerUser() {
        Log.d("MYTAG", "registerUser: ${id.value}  ${password.value}  ${nickname.value}")
        viewModelScope.launch {
            _currentState.emit(true)
            registerUserUseCase.execute(
                UserRequest(
                    id.value,
                    nickname.value,
                    password.value,
                    null,
                    null
                )
            ).collect {
                checkState(it)
            }
        }
    }

    fun duplicateNickName() {
        viewModelScope.launch {
            _currentState.emit(true)
            duplicateNickNameUseCase.execute(nickname.value).collect {
                checkState(it)
            }
        }
    }

    fun sendEmailAuth() {
        Log.d("MYTAG", "sendEmailAuth: ${id.value}")
        viewModelScope.launch {
            _currentState.emit(true)
            sendEmailAuthUseCase.execute(id.value).collect {
                _authButtonState.emit(checkState(it))
            }
        }
    }

    fun sendEmailAuthNum() {
        viewModelScope.launch {
            _currentState.emit(true)
            sendEmailAuthNumUseCase.execute(verificationCode.value, id.value).collect {
                _resetButtonState.emit(checkState(it))
            }
        }
    }

    private suspend fun <T> checkState(data: DataState<T>): Boolean {
        when (data) {
            is DataState.Success -> {
                _currentState.emit(false)
                return true
            }

            is DataState.Loading -> {
                _currentState.emit(true)
                return false
            }

            is DataState.Error -> {
                _currentState.emit(false)
                return false
            }
        }
    }
}
