package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.data.model.response.AnnouncementResponse
import com.ssafy.tranvel.data.remote.datasource.announcement.AnnouncementRemoteDataSource
import com.ssafy.tranvel.domain.repository.AnnouncementRepository
import retrofit2.Response
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor(
    private val announcementRemoteDataSource: AnnouncementRemoteDataSource,
) : AnnouncementRepository {
    override suspend fun getAllAnnouncement(page: Int): Response<AnnouncementResponse> =
        announcementRemoteDataSource.getAllAnnouncements(page)
}