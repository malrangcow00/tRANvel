package com.ssafy.tranvel.presentation.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.domain.usecase.history.GetHistoryUseCase
import com.ssafy.tranvel.domain.viewstate.history.HistoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {
    private val config = PagingConfig(pageSize = 10)
    var cnt = 0;

    fun createInitialState() = HistoryViewState()
    private val initialState:HistoryViewState by lazy{createInitialState()}
    private val _uiState : MutableStateFlow<HistoryViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<HistoryViewState> = _uiState
    val currentState:HistoryViewState get() = uiState.value
    protected fun setState(reduce : HistoryViewState.()->HistoryViewState){
        val newState = currentState.reduce()
        _uiState.value=newState
    }

    init {
        getAllHistories()
    }

    private fun getAllHistories(){
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }

            val params = GetHistoryUseCase.Params(config)
            val pagedFlow = getHistoryUseCase(params).cachedIn(scope = viewModelScope)
            delay(2000)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }
}
