package com.ssafy.tranvel.domain.viewstate.announcement

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.AnnouncementResult
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.domain.viewstate.IViewState
import kotlinx.coroutines.flow.Flow

@Stable
data class AnnouncementViewState(
    val isLoading: Boolean = false,
    val pagedData: Flow<PagingData<AnnouncementDto>>? = null,
    val data : List<AnnouncementResult>? = null
) : IViewState
