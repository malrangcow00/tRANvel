package com.ssafy.tranvel.presentation.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ssafy.tranvel.domain.usecase.history.GetDetailHistoryRecordUseCase
import com.ssafy.tranvel.domain.viewstate.history.DetailHistoryRecordViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DeHisRecoViewMO_싸피"

@HiltViewModel
class DetailHistoryRecordViewModel @Inject constructor(
    private val getDetailHistoryRecordUseCase: GetDetailHistoryRecordUseCase
) : ViewModel(){
    private val config = PagingConfig(pageSize = 10)
    var cnt = 0

    fun createInitialState() = DetailHistoryRecordViewState()
    private val initialState: DetailHistoryRecordViewState by lazy { createInitialState() }
    private val _uiState: MutableStateFlow<DetailHistoryRecordViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<DetailHistoryRecordViewState> = _uiState
    val currentState: DetailHistoryRecordViewState get() = uiState.value
    protected fun setState(reduce: DetailHistoryRecordViewState.() -> DetailHistoryRecordViewState) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    fun getDetailHistoryRecord(roomId : Long) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            val params = GetDetailHistoryRecordUseCase.Params(config, roomId)
            val pagedFlow = getDetailHistoryRecordUseCase(params).cachedIn(scope = viewModelScope)
            delay(2000)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }
}
