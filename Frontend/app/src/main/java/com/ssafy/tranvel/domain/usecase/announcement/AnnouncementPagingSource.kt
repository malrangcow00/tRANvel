package com.ssafy.tranvel.domain.usecase.announcement

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.data.model.dto.extension.toAnnouncementDtoList
import com.ssafy.tranvel.domain.repository.AnnouncementRepository
import java.io.IOException

class AnnouncementPagingSource(
    internal val repository: AnnouncementRepository,
) : PagingSource<Int, AnnouncementDto>() {
    override fun getRefreshKey(state: PagingState<Int, AnnouncementDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnnouncementDto> {
        val page = params.key ?: 1
        return try {
            val response = repository.getAllAnnouncement(page)

            val announcementList = if (response.isSuccessful) {
                response.body()?.results.orEmpty().toAnnouncementDtoList()
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