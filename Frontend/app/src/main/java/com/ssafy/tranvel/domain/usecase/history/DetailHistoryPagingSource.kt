package com.ssafy.tranvel.domain.usecase.history

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.tranvel.data.model.dto.DetailHistoryDto
import com.ssafy.tranvel.data.model.dto.extension.toDetailHistoryDtoList
import com.ssafy.tranvel.domain.repository.HistoryRepository
import java.io.IOException

class DetailHistoryPagingSource (
    internal val historyRepository : HistoryRepository,
    private val roomId : Long
) : PagingSource<Int, DetailHistoryDto>(){
    override fun getRefreshKey(state: PagingState<Int, DetailHistoryDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailHistoryDto> {
        val page = params.key ?: 1
        return try {
            val response = historyRepository.getDetailHistory(roomId)

            val detailHistoryList = if (response.isSuccessful) {
                response.body()?.data.orEmpty().toDetailHistoryDtoList()
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
