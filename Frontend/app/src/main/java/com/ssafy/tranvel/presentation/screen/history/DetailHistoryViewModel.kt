package com.ssafy.tranvel.presentation.screen.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ssafy.tranvel.data.model.AdjustmentHistoryResult
import com.ssafy.tranvel.data.model.AttractionHistoryResult
import com.ssafy.tranvel.data.model.FoodHistoryResult
import com.ssafy.tranvel.data.model.response.AdjustmentHistoryResponse
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.HistoryRepository
import com.ssafy.tranvel.domain.usecase.history.GetAdjustmentHistoryUseCase
import com.ssafy.tranvel.domain.usecase.history.GetAttractionHistoryUseCase
import com.ssafy.tranvel.domain.usecase.history.GetFoodHistoryUseCase
import com.ssafy.tranvel.domain.viewstate.history.AdjustmentHistoryViewState
import com.ssafy.tranvel.domain.viewstate.history.AttractionHistoryViewState
import com.ssafy.tranvel.domain.viewstate.history.FoodHistoryViewState
import com.ssafy.tranvel.presentation.screen.login.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DeHiViMo_싸피"

@HiltViewModel
class DetailHistoryViewModel @Inject constructor(
    private val getAdjustmentHistoryUseCase: GetAdjustmentHistoryUseCase,
    private val getAttractionHistoryUseCase: GetAttractionHistoryUseCase,
    private val getFoodHistoryUseCase: GetFoodHistoryUseCase,
    private val historyRepository : HistoryRepository
) : ViewModel() {
    var adjustmentCnt = 0
    var attractionCnt = 0
    var foodCnt = 0

    var adjustmentList : List<AdjustmentHistoryResult> = mutableListOf()
    var attractionList : List<AttractionHistoryResult> = mutableListOf()
    var foodList : List<FoodHistoryResult> = mutableListOf()

    private val _currentState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentState: StateFlow<Boolean> = _currentState

    private val _uiAdjustmentState: MutableStateFlow<String> = MutableStateFlow("")
    val uiAdjustmentState: StateFlow<String> = _uiAdjustmentState
    private val _currentAdjustmentState: MutableStateFlow<Boolean> = MutableStateFlow(false)
//        val currentAdjustmentState: StateFlow<Boolean> = _currentAdjustmentState


    fun getAdjustmentHistory(roomId : Long) {
        viewModelScope.launch {
            _currentAdjustmentState.emit(true)
            getAdjustmentHistoryUseCase.execute(roomId).collect{
                when (it) {
                    is DataState.Success -> {
                        _uiAdjustmentState.emit("SUCCESS")
                        _currentAdjustmentState.emit(true)
                        Log.d(TAG, "getAdjustmentHistory: ${it.data}")
                        adjustmentList = it.data.data
                    }

                    is DataState.Error -> {
                        _uiAdjustmentState.emit("ERROR")
                        _currentAdjustmentState.emit(false)
                    }

                    is DataState.Loading -> {
                        _uiAdjustmentState.emit("LOADING")
                        _currentAdjustmentState.emit(true)
                    }
                }
            }
        }
    }

    private val _uiAttractionState: MutableStateFlow<String> = MutableStateFlow("")
    val uiAttractionState: StateFlow<String> = _uiAttractionState
    private val _currentAttractionState: MutableStateFlow<Boolean> = MutableStateFlow(false)
//        val currentAdjustmentState: StateFlow<Boolean> = _currentAdjustmentState

    fun getAttractionHistory(roomId : Long) {
        viewModelScope.launch {
            _currentAttractionState.emit(true)
            getAttractionHistoryUseCase.execute(roomId).collect{
                when (it) {
                    is DataState.Success -> {
                        _uiAttractionState.emit("SUCCESS")
                        _currentAttractionState.emit(true)
                        Log.d(TAG, "getAttractionHistory: ${it.data}")
                        attractionList = it.data.data
                        (it.data.data).forEach{
                            Log.d(TAG, "getAttractionHistory: 가져오는 값 :${it.attractionList} ")
                        }
                    }

                    is DataState.Error -> {
                        _uiAttractionState.emit("ERROR")
                        _currentAttractionState.emit(false)
                    }

                    is DataState.Loading -> {
                        _uiAttractionState.emit("LOADING")
                        _currentAttractionState.emit(true)
                    }
                }
            }
        }
    }

    private val _uiFoodState: MutableStateFlow<String> = MutableStateFlow("")
    val uiFoodState: StateFlow<String> = _uiFoodState
    private val _currentFoodState: MutableStateFlow<Boolean> = MutableStateFlow(false)
//        val currentFoodState: StateFlow<Boolean> = _currentFoodState

    fun getFoodHistory(roomId : Long) {
        viewModelScope.launch {
            _currentFoodState.emit(true)
            getFoodHistoryUseCase.execute(roomId).collect{
                when (it) {
                    is DataState.Success -> {
                        _uiFoodState.emit("SUCCESS")
                        _currentFoodState.emit(true)
                        Log.d(TAG, "getFoodHistory: ${it.data}")
                        foodList = it.data.data
                    }

                    is DataState.Error -> {
                        _uiFoodState.emit("ERROR")
                        _currentFoodState.emit(false)
                    }

                    is DataState.Loading -> {
                        _uiFoodState.emit("LOADING")
                        _currentFoodState.emit(true)
                    }
                }
            }
        }
    }

    private suspend fun <T> checkState(data: DataState<T>): Boolean {
        when (data) {
            is DataState.Success -> {
                Log.d("MYTAG", "checkState: ${data.data}")
                _currentState.emit(true)
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
