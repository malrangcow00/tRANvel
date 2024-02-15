package com.ssafy.tranvel.domain.usecase.history

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.tranvel.data.model.dto.DetailHistoryRecordDto
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.data.model.dto.extension.toAnnouncementDtoList
import com.ssafy.tranvel.data.model.dto.extension.toDetailHistoryRecordDto
import com.ssafy.tranvel.data.model.dto.extension.toHistoryDtoList
import com.ssafy.tranvel.domain.repository.HistoryRepository
import java.io.IOException

class DetailHistoryRecordPagingSource (
    internal val historyRepository : HistoryRepository,
    private val userId : Long
) : PagingSource<Int, DetailHistoryRecordDto>(){
    override fun getRefreshKey(state: PagingState<Int, DetailHistoryRecordDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailHistoryRecordDto> {
        val page = params.key ?: 1
        return try {
            val response = historyRepository.getDetailHistoryRecord(userId)

            val detailHistoryList = if (response.isSuccessful) {
                response.body()?.data.orEmpty().toDetailHistoryRecordDto()
            } else {
                emptyList()
            }

            LoadResult.Page(
                data = detailHistoryList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (detailHistoryList.isEmpty()) null else page + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

}
