package com.ssafy.tranvel.presentation.screen.register

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.data.model.request.UserRequest
import com.ssafy.tranvel.domain.usecase.register.DuplicateNickNameUseCase
import com.ssafy.tranvel.domain.usecase.register.RegisterUserUseCase
import com.ssafy.tranvel.domain.usecase.register.SendEmailAuthNumUseCase
import com.ssafy.tranvel.domain.usecase.register.SendEmailAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
    val verificationCode: MutableState<String> = mutableStateOf("")

    fun setBitmap(bitmap: Bitmap) {
        viewModelScope.launch {
            _bitmap.emit(bitmap)
        }
    }

    fun registerUser() {
        viewModelScope.launch {
            registerUserUseCase.execute(UserRequest(id.value, nickname.value, password.value))
        }
    }

    fun duplicateNickName() {
        viewModelScope.launch {
            duplicateNickNameUseCase.execute(nickname.value)
        }
    }
}