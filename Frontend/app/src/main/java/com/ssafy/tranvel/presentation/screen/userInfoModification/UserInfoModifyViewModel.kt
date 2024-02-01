package com.ssafy.tranvel.presentation.screen.userInfoModification

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.domain.usecase.register.DuplicateNickNameUseCase
import com.ssafy.tranvel.domain.usecase.userInfoModification.ModifyProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class UserInfoModifyViewModel @Inject constructor(
    private val modifyProfileImageUseCase : ModifyProfileImageUseCase,
    private val duplicateNickNameUseCase: DuplicateNickNameUseCase,
) : ViewModel() {

    private val _bitmap : MutableStateFlow<Bitmap?> = MutableStateFlow(null)
    val bitmap : StateFlow<Bitmap?> = _bitmap

    val id : MutableState<String> = mutableStateOf("")
    val nickname : MutableState<String> = mutableStateOf("")
    val password : MutableState<String> = mutableStateOf("")

    private val _currentState : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentState : StateFlow<Boolean> = _currentState

    fun setBitmap(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

    fun checkPassword(): Boolean {
        val validation = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""
        val pattern = Pattern.compile(validation)
        if (password.value == "") {
            return true
        }

        return pattern.matcher(password.value).matches()
    }


}
