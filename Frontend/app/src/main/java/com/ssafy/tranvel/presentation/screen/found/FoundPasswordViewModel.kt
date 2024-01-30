package com.ssafy.tranvel.presentation.screen.found

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.usecase.register.ResetPasswordUseCase
import com.ssafy.tranvel.domain.usecase.register.SendEmailAuthNumUseCase
import com.ssafy.tranvel.domain.usecase.register.SendEmailAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FoundPasswordViewModel"

@HiltViewModel
class FoundPasswordViewModel @Inject constructor(
    private val sendEmailAuthUseCase: SendEmailAuthUseCase,
    private val sendEmailAuthNumUseCase: SendEmailAuthNumUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    val id: MutableState<String> = mutableStateOf("")
    val verificationCode: MutableState<String> = mutableStateOf("")

    private val _authButtonState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val authButtonState: StateFlow<Boolean> = _authButtonState

    private val _resetButtonState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val resetButtonState: StateFlow<Boolean> = _resetButtonState

    private val _currentState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentState: StateFlow<Boolean> = _currentState


    fun sendEmailAuth() {
        viewModelScope.launch {
            _currentState.emit(true)
            sendEmailAuthUseCase.execute(id.value).collect {
                checkState(it)
            }
        }
    }

    fun sendEmailAuthNum() {
        viewModelScope.launch {
            _currentState.emit(true)
            sendEmailAuthNumUseCase.execute(verificationCode.value, id.value).collect {
                checkState(it)
            }
        }
    }

    private suspend fun<T> checkState(data: DataState<T>) {
            when (data) {
                is DataState.Success -> {
                    _currentState.emit(false)
                }

                is DataState.Loading -> {
                    _currentState.emit(true)
                }

                is DataState.Error -> {
                    _currentState.emit(false)
                }
            }
    }

    fun resetPassword() {
        viewModelScope.launch {
            _currentState.emit(true)
            resetPasswordUseCase.execute(id.value).collect {
                checkState(it)
            }
        }
    }
}