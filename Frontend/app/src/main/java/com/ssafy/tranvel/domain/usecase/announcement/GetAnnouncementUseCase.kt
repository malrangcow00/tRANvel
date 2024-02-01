package com.ssafy.tranvel.domain.usecase.announcement

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.domain.repository.AnnouncementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnnouncementUseCase @Inject constructor(
    internal val repository : AnnouncementRepository
){
    data class Params(
        val pagingConfig: PagingConfig
    )

    operator fun invoke(param: Params) : Flow<PagingData<AnnouncementDto>> {
        return Pager(
            config = param.pagingConfig,
            pagingSourceFactory = { AnnouncementPagingSource(repository)}
        ).flow
    }
}
