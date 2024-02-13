package com.ssafy.tranvel.presentation.screen.travel

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.CameraPositionState
import com.ssafy.tranvel.BuildConfig
import com.ssafy.tranvel.data.model.dto.AdjustmentGameHistory
import com.ssafy.tranvel.data.model.dto.AttractionInfo
import com.ssafy.tranvel.data.model.dto.FoodGameDto
import com.ssafy.tranvel.data.model.dto.RandomDto
import com.ssafy.tranvel.data.model.dto.STOMPDto
import com.ssafy.tranvel.data.model.dto.UserInfo
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.TokenRepository
import com.ssafy.tranvel.domain.repository.TravelRepository
import com.ssafy.tranvel.presentation.screen.login.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class GameViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val travelRepository: TravelRepository
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

    private val _accountUiState = MutableStateFlow(false)
    val accountUiState = _accountUiState.asStateFlow()

    private val _userList = MutableStateFlow<List<UserInfo>>(listOf())
    val userList = _userList.asStateFlow()

    private val _networkResult = MutableStateFlow(false)
    val networkResult = _networkResult.asStateFlow()

    private val _bitmap: MutableStateFlow<Bitmap?> = MutableStateFlow(null)
    val bitmap: StateFlow<Bitmap?> = _bitmap

    private val _navigateFoodScreen = MutableStateFlow(false)
    val navigateFoodScreen = _navigateFoodScreen.asStateFlow()

    //뽑을 사람의 상태
    private val _drawPerson = MutableStateFlow(false)
    val drawPerson = _drawPerson.asStateFlow()

    //방장의 차례인지 아닌지
    private val _drawState = MutableStateFlow(true)
    val drawState = _drawState.asStateFlow()

    private val _attractionState = MutableStateFlow(true)
    val attractionState = _attractionState.asStateFlow()

    private val _foodGameData = MutableStateFlow(FoodGameDto("", "", listOf(), listOf(), listOf()))
    val foodGameData = _foodGameData.asStateFlow()

    private val _latLng = MutableStateFlow(LatLng(37.532600, 127.024612))
    val latLng = _latLng.asStateFlow()

    private val _attractionInfo = MutableStateFlow(AttractionInfo("", "", "", "37.390791", "127.096306", "", "", ""))
    val attractionInfo = _attractionInfo.asStateFlow()

    private val _random = MutableStateFlow(RandomDto("", 0f, 0L))
    val random = _random.asStateFlow()

    private val _foodScreen = MutableStateFlow(false)
    val foodScreen = _foodScreen.asStateFlow()

    private val _cameraState = MutableStateFlow(CameraPositionState())
    val cameraState = _cameraState.asStateFlow()
    fun setAttractionLangLng(latLng: LatLng) {
        _latLng.value = latLng
    }

    fun setDrawPerson() {
        _drawPerson.value = false
    }

    fun setBitmap(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

    fun setNetworkResult() {
        _networkResult.value = false
    }

    fun setNavigateFoodState() {
        _navigateFoodScreen.value = false
    }

    fun setAdjustmentGameHistory(
        selectedUserList: List<Long>,
        price: Int,
        category: String,
        content: String,
        file: File?
    ) {
        val history = AdjustmentGameHistory(
            RoomInfo.roomID,
            price,
            selectedUserList,
            category,
            content,
            "부산",
        )
        val image = file?.asRequestBody("image/*".toMediaTypeOrNull())
        val requestImage = image?.let { MultipartBody.Part.createFormData("image", file.name, it) }
        val data = Gson().toJson(history).toRequestBody("application/json".toMediaTypeOrNull())
        viewModelScope.launch {
            _accountUiState.emit(true)
            travelRepository.setAdjustmentGameHistory(
                data,
                requestImage
            ).collect {
                when (it) {
                    is DataState.Success -> {
                        Log.d("TAG", "setAdjustmentGameHistory: ${it.data.data}")
                        _accountUiState.emit(false)
                        _networkResult.emit(true)
                    }

                    is DataState.Error -> {
                        _accountUiState.emit(false)
                    }

                    is DataState.Loading -> {
                        _accountUiState.emit(true)
                    }
                }
            }
        }
    }

    fun getUserList(roomId: Long) {
        viewModelScope.launch {
            _accountUiState.emit(true)
            travelRepository.getUserList(roomId).collect {
                when (it) {
                    is DataState.Success -> {
                        _userList.emit(it.data.data)
                        _accountUiState.emit(false)
                    }

                    is DataState.Error -> {
                        _accountUiState.emit(false)
                    }

                    is DataState.Loading -> {
                        _accountUiState.emit(true)
                    }
                }
            }
        }
    }

    fun setFoodGameData(foodGameDto: FoodGameDto) {
        viewModelScope.launch {
            _foodGameData.emit(foodGameDto)
        }
    }

    fun setFoodGameRandom(randomDto: RandomDto) {
        viewModelScope.launch {
            Log.d("TAG", "setFoodGameRandom: $randomDto")
            _random.emit(randomDto)
        }
    }

    fun setAttractionState() {
        _attractionState.value = false
    }

    fun setFoodScreenState() {
        _foodScreen.value = false
    }

    @SuppressLint("CheckResult")
    fun runStomp(roomId: Long) {

        stompClient.topic("/topic/tranvel/rooms/$roomId")
            .doOnError { Log.i("message Recieve1", "에러남") }
            .subscribe { topicMessage ->
                Log.i("message Recieve2", topicMessage.payload)
            }

        stompClient.topic("/topic/tranvel/getplayer/$roomId")
            .doOnError { Log.i("message Recieve1", "에러남") }
            .subscribe { topicMessage ->
                val a = Gson().fromJson(topicMessage.payload, STOMPDto::class.java)
                _drawState.value = false
                if (a.message == User.nickName) {
                    _drawPerson.value = true
                }
                Log.i("message Recieve2", topicMessage.payload)
            }

        stompClient.topic("/topic/tranvel/attractionrandom/$roomId")
            .doOnError { Log.i("message Recieve1", "에러남") }
            .subscribe { topicMessage ->
                setDrawPerson()
                _attractionInfo.value = Gson().fromJson(topicMessage.payload, AttractionInfo::class.java)
                _drawState.value = true
                _attractionState.value = true
                Log.i("message Recieve2", topicMessage.payload)
            }

        //음식 게임 입장
        stompClient.topic("/topic/tranvel/foodgame/$roomId")
            .doOnError { Log.i("message Recieve1", "에러남") }
            .subscribe { topicMessage ->
                val a = Gson().fromJson(topicMessage.payload, STOMPDto::class.java)
                Log.d("TAG", "runStomp: $a")
                if (a.type == "CLOSE") {
                    setNavigateFoodState()
                    _foodScreen.value = true
                } else {
                    if (!RoomInfo.authority) {
                        _navigateFoodScreen.value = true
                    }
                }
            }

        //음식 게임 메뉴 전송
        stompClient.topic("/topic/tranvel/foodgameready/$roomId")
            .doOnError { Log.i("message Recieve1", "에러남") }
            .subscribe { topicMessage ->
                Log.d("TAG", "runStomp: ${topicMessage.payload}")
                setFoodGameData(Gson().fromJson(topicMessage.payload, FoodGameDto::class.java))
            }

        //음식 뽑기 시작
        stompClient.topic("/topic/tranvel/foodgamestart/$roomId")
            .doOnError { Log.i("message Recieve1", "에러남") }
            .subscribe { topicMessage ->
                setFoodGameRandom(Gson().fromJson(topicMessage.payload, RandomDto::class.java))
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

    fun sendAttractionPersonMessage(type: String, message: String) {
        val data = JSONObject()
        data.put("type", type)
        data.put("roomId", RoomInfo.roomID)
        data.put("sender_id", User.id)
        data.put("message", message)
        stompClient.send("/app/tranvel/getplayer", data.toString())
            .doOnError { Log.i("message Recieve5", "에러남") }
            .subscribe()
    }

    fun sendAttractionDrawMessage() {
        val data = JSONObject()
        data.put("sender_id", User.id)
        data.put("roomId", RoomInfo.roomID)
        data.put("message", "")
        data.put("latitude", _latLng.value.latitude)
        data.put("longitude", _latLng.value.longitude)
        stompClient.send("/app/tranvel/attractionrandom", data.toString())
            .doOnError { Log.i("message Recieve5", "에러남") }
            .subscribe()
    }

    fun sendFoodGameMessage(type: String, message: String) {
        val data = JSONObject()
        data.put("type", type)
        data.put("roomId", RoomInfo.roomID)
        data.put("sender_id", User.id)
        data.put("message", message)
        stompClient.send("/app/tranvel/getplayer", data.toString())
            .doOnError { Log.i("message Recieve5", "에러남") }
            .subscribe()
    }

    fun sendFoodGameReadyMessage(type: String, message: String) {
        val data = JSONObject()
        data.put("type", type)
        data.put("roomId", RoomInfo.roomID)
        data.put("sender_id", User.id)
        data.put("message", message)
        stompClient.send("/app/tranvel/foodgameready", data.toString())
            .doOnError { Log.i("message Recieve5", "에러남") }
            .subscribe()
    }

    fun sendFoodGameStartMessage(type: String, message: String) {
        val data = JSONObject()
        data.put("type", type)
        data.put("roomId", RoomInfo.roomID)
        data.put("sender_id", User.id)
        data.put("message", message)
        stompClient.send("/app/tranvel/foodgamestart", data.toString())
            .doOnError { Log.i("message Recieve5", "에러남") }
            .subscribe()
    }

    fun sendRoomMessage(type: String) {
        val data = JSONObject()
        data.put("type", type)
        data.put("roomId", RoomInfo.roomID)
        data.put("sender_id", User.id)
        data.put("message", "")
        stompClient.send("/app/tranvel/rooms", data.toString())
            .doOnError { Log.i("message Recieve5", "에러남") }
            .subscribe()
    }
}

interface ButtonClick {
    fun onClick()
}