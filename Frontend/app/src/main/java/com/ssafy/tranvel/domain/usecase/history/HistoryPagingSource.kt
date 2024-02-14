package com.ssafy.tranvel.domain.usecase.history

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.data.model.dto.extension.toAnnouncementDtoList
import com.ssafy.tranvel.data.model.dto.extension.toHistoryDtoList
import com.ssafy.tranvel.domain.repository.HistoryRepository
import java.io.IOException

class HistoryPagingSource (
    internal val historyRepository : HistoryRepository,
    private val userId : Long
) : PagingSource<Int, HistoryDto>(){
    override fun getRefreshKey(state: PagingState<Int, HistoryDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryDto> {
        val page = params.key ?: 1
        return try {
            val response = historyRepository.getAllHistories(userId)

            val historyList = if (response.isSuccessful) {
                response.body()?.data.orEmpty().toHistoryDtoList()
            } else {
                emptyList()
            }

            LoadResult.Page(
                data = historyList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (historyList.isEmpty()) null else page + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

}
