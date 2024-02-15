package com.ssafy.tranvel.presentation.screen.userInfoModification

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.data.utils.DataState
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
    val passwordCheck : MutableState<String> = mutableStateOf("")

    private val _currentState : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentState : StateFlow<Boolean> = _currentState

    fun setBitmap(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

    fun duplicateNickName() {
        Log.d("MYTAG", "duplicateNickName: ${id.value} ${nickname.value}")
        viewModelScope.launch {
            _currentState.emit(true)
            duplicateNickNameUseCase.execute(nickname.value, id.value).collect {
                checkState(it)
            }
        }
    }



    private suspend fun <T> checkState(data: DataState<T>): Boolean {
        when (data) {
            is DataState.Success -> {
                Log.d("MYTAG", "checkState: ${data.data}")
                _currentState.emit(false)
                return true
            }

            is DataState.Loading -> {
                _currentState.emit(true)
                return false
            }

            is DataState.Error -> {
                Log.d("MYTAG", "checkState: ${data.apiError.toString()}")
                _currentState.emit(false)
                return false
            }
        }
    }
}
