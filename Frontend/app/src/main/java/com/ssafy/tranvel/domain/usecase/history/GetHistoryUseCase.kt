package com.ssafy.tranvel.domain.usecase.history

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.dto.AllHistoryDto
import com.ssafy.tranvel.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val historyRepository : HistoryRepository
){
    data class Params(
        val pagingConfig : PagingConfig
    )

    operator fun invoke(param: Params) : Flow<PagingData<AllHistoryDto>>{
        return Pager(
            config = param.pagingConfig,
            pagingSourceFactory = { HistoryPagingSource(historyRepository) }
        ).flow
    }
}
