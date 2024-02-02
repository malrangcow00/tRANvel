package com.ssafy.tranvel.presentation.screen.announcement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ssafy.tranvel.domain.usecase.announcement.GetAnnouncementUseCase
import com.ssafy.tranvel.domain.viewstate.announcement.AnnouncementViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val getAnnouncementUseCase: GetAnnouncementUseCase,
) : ViewModel() {
    private val config = PagingConfig(pageSize = 20)

    //state 처리
    fun createInitialState() = AnnouncementViewState()
    private val initialState: AnnouncementViewState by lazy { createInitialState() }
    private val _uiState: MutableStateFlow<AnnouncementViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<AnnouncementViewState> = _uiState
    val currentState: AnnouncementViewState get() = uiState.value
    protected fun setState(reduce: AnnouncementViewState.() -> AnnouncementViewState) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    init {
        getAllAnnouncements()
    }

    private fun getAllAnnouncements() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }

            val params = GetAnnouncementUseCase.Params(config)
            val pagedFlow = getAnnouncementUseCase(params).cachedIn(scope = viewModelScope)
            delay(1000)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }
}
