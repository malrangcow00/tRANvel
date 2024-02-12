package com.ssafy.tranvel.presentation.screen.history

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.domain.usecase.history.GetDetailHistoryUseCase
import com.ssafy.tranvel.domain.usecase.history.GetHistoryUseCase
import com.ssafy.tranvel.domain.viewstate.history.DetailHistoryViewState
import com.ssafy.tranvel.domain.viewstate.history.HistoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DeHiViMo_싸피"

@HiltViewModel
class DetailHistoryViewModel @Inject constructor(
    private val getDetailHistoryUseCase: GetDetailHistoryUseCase,
) : ViewModel() {
    private val config = PagingConfig(pageSize = 10)
    var cnt = 0

    fun createInitialState() = DetailHistoryViewState()
    private val initialState: DetailHistoryViewState by lazy { createInitialState() }
    private val _uiState: MutableStateFlow<DetailHistoryViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<DetailHistoryViewState> = _uiState
    val currentState: DetailHistoryViewState get() = uiState.value
    protected fun setState(reduce: DetailHistoryViewState.() -> DetailHistoryViewState) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    init {
        getDetailHistory(-1)
    }

    fun getDetailHistory(roomId : Long) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            val params = GetDetailHistoryUseCase.Params(config, roomId)
            val pagedFlow = getDetailHistoryUseCase(params).cachedIn(scope = viewModelScope)
            delay(2000)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }
}
