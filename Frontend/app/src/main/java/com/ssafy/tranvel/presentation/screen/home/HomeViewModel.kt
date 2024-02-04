package com.ssafy.tranvel.presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
//import com.ssafy.tranvel.domain.usecase.history.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val getHistoryUseCase : GetHistoryUseCase
):ViewModel() {
    val enterCode : MutableState<String> = mutableStateOf("")

    private val _uiState : MutableStateFlow<String> = MutableStateFlow("")
    val uiState : StateFlow<String> = _uiState

    private val _currentState : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentState : StateFlow<Boolean> = _currentState

//    fun checkEnterCode() : Boolean{
//        viewModelScope.launch {
//
//        }
//    }
}
