package com.ssafy.tranvel.domain.usecase.history

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.dto.DetailHistoryRecordDto
import com.ssafy.tranvel.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailHistoryRecordUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    data class Params(
        val pagingConfig : PagingConfig,
        val roomId : Long
    )

    operator fun invoke(param: Params) : Flow<PagingData<DetailHistoryRecordDto>> {
        return Pager(
            config = param.pagingConfig,
            pagingSourceFactory = { DetailHistoryRecordPagingSource(historyRepository,param.roomId) }
        ).flow
    }
}
