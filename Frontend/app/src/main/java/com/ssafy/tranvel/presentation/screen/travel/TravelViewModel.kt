package com.ssafy.tranvel.presentation.screen.travel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tranvel.BuildConfig
import com.ssafy.tranvel.data.local.PreferenceDataSource
import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.TokenRepository
import com.ssafy.tranvel.domain.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(
    private val travelRepository: TravelRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    val result = tokenRepository.getToken()
    private val url = BuildConfig.WEBSOCKET_URL// 소켓에 연결하는 엔드포인트가 /socket일때 다음과 같음
    val stompClient = Stomp.over(
        Stomp.ConnectionProvider.OKHTTP,
        url,
        mapOf("Authorization" to "Bearer ${result}")
    )

    private val _roomBodyState = MutableStateFlow(false)
    val roomBodyState = _roomBodyState.asStateFlow()

    private val _currentState = MutableStateFlow(false)
    val currentState = _currentState.asStateFlow()

    private fun changeCurrentState() {
        viewModelScope.launch {
            _currentState.emit(!_currentState.value)
        }
    }

    fun changeRoomBodyState() {
        viewModelScope.launch {
            _roomBodyState.emit(!_roomBodyState.value)
        }
    }

    @SuppressLint("CheckResult")
    fun runStomp(roomId: Int) {

        stompClient.topic("/topic/tranvel/room/$roomId")
            .doOnError { Log.i("message Recieve1", "에러남") }
            .subscribe { topicMessage ->
                Log.i("message Recieve2", topicMessage.payload)
            }

        stompClient.connect()

        stompClient.lifecycle()
            .subscribe { lifecycleEvent ->
                when (lifecycleEvent.type) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.i("OPEND", "!!")
                        changeCurrentState()
                        _roomBodyState.value = true
                    }

                    LifecycleEvent.Type.CLOSED -> {
                        Log.i("CLOSED", "!!")
                    }

                    LifecycleEvent.Type.ERROR -> {
                        Log.i("ERROR", "!!")
                        Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                        stompClient.connect()
                    }

                    else -> {
                        Log.i("ELSE", lifecycleEvent.message)
                    }
                }
            }
    }

    fun sendRoomMessage(type: String) {
        val data = JSONObject()
        data.put("type", type)
        data.put("roomId", 5)
        data.put("sender_id", 11)
        data.put("message", "")
        stompClient.send("/app/tranvel/rooms", data.toString())
            .doOnError { Log.i("message Recieve5", "에러남") }
            .subscribe()
    }

    fun createRoom(password: String) {
        viewModelScope.launch {
            _currentState.emit(true)
            travelRepository.createRoom(Room(roomPassword = password)).collect {
                when (it) {
                    is DataState.Success -> {
                        runStomp(7)
                    }

                    is DataState.Error -> {
                        _currentState.emit(false)
                    }

                    is DataState.Loading -> {
                        _currentState.emit(true)
                    }
                }
            }
        }
    }

    fun enterRoom() {

    }
}