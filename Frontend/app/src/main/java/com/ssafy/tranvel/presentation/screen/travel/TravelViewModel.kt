package com.ssafy.tranvel.presentation.screen.travel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.BuildConfig
import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.TokenRepository
import com.ssafy.tranvel.domain.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(
    private val travelRepository: TravelRepository
) : ViewModel() {

    private val _roomBodyState = MutableStateFlow(false)
    val roomBodyState = _roomBodyState.asStateFlow()

    private val _currentState = MutableStateFlow(false)
    val currentState = _currentState.asStateFlow()

    fun changeRoomBodyState() {
        viewModelScope.launch {
            _roomBodyState.emit(!_roomBodyState.value)
        }
    }

    fun createRoom(password: String) {
        viewModelScope.launch {
            _currentState.emit(true)
            travelRepository.createRoom(password).collect {
                extracted(it)
            }
        }
    }

    private suspend fun extracted(it: DataState<DataResponse<Room<Boolean>>>) {
        when (it) {
            is DataState.Success -> {
                RoomInfo.roomID = it.data.data.roomId
                RoomInfo.roomCode = it.data.data.roomCode
                RoomInfo.roomPassword = it.data.data.roomPassword
                RoomInfo.authority = it.data.data.authority
                _currentState.emit(false)
                _roomBodyState.value = true
            }

            is DataState.Error -> {
                _currentState.emit(false)
            }

            is DataState.Loading -> {
                _currentState.emit(true)
            }
        }
    }

    fun enterRoom(code: String, password: String) {
        viewModelScope.launch {
            _currentState.emit(true)
            travelRepository.enterRoom(code, password).collect {
                extracted(it)
            }
        }
    }
}