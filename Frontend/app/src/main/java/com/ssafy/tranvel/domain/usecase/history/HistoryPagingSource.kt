package com.ssafy.tranvel.domain.usecase.history

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.tranvel.data.model.dto.AllHistoryDto
import com.ssafy.tranvel.data.model.dto.extension.toAnnouncementDtoList
import com.ssafy.tranvel.domain.repository.HistoryRepository
import java.io.IOException

class HistoryPagingSource (
    internal val historyRepository : HistoryRepository,
) : PagingSource<Int, AllHistoryDto>(){
    override fun getRefreshKey(state: PagingState<Int, AllHistoryDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllHistoryDto> {
        val page = params.key ?: 1
        return try {
            val response = historyRepository.getAllHistory()

            val announcementList = if (response.isSuccessful) {
                response.body()?.data.orEmpty().toAnnouncementDtoList()
            } else {
                emptyList()
            }

            LoadResult.Page(
                data = announcementList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (announcementList.isEmpty()) null else page + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

}
