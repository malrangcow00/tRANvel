package com.ssafy.tranvel.presentation.screen.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.domain.usecase.travel.CreateRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(
    private val createRoomUseCase: CreateRoomUseCase
) : ViewModel() {

    fun createRoom(password: String) {
        viewModelScope.launch {
            createRoomUseCase.execute(password)
        }
    }
}