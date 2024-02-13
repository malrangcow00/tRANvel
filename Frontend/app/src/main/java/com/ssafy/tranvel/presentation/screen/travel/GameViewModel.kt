package com.ssafy.tranvel.presentation.screen.travel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.ssafy.tranvel.BuildConfig
import com.ssafy.tranvel.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {
    val result = tokenRepository.getToken()
    private val url = BuildConfig.WEBSOCKET_URL// 소켓에 연결하는 엔드포인트가 /socket일때 다음과 같음
    val stompClient = Stomp.over(
        Stomp.ConnectionProvider.OKHTTP,
        url,
        mapOf("Access-token" to "Bearer ${result}")
    )

    init {
        runStomp(RoomInfo.roomID)
    }

    @SuppressLint("CheckResult")
    fun runStomp(roomId: Long) {

        stompClient.topic("/topic/tranvel/rooms/$roomId")
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
                        sendRoomMessage("ENTER")
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
        data.put("roomId", RoomInfo.roomID)
        data.put("sender_id", 32)
        data.put("message", "")
        stompClient.send("/app/tranvel/rooms", data.toString())
            .doOnError { Log.i("message Recieve5", "에러남") }
            .subscribe()
    }
}