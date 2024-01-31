package com.ssafy.tranvel.data.remote.datasource.announcement.impl

import com.ssafy.tranvel.data.model.response.AnnouncementResponse
import com.ssafy.tranvel.data.remote.api.AnnouncementService
import com.ssafy.tranvel.data.remote.datasource.BaseDataSource
import com.ssafy.tranvel.data.remote.datasource.announcement.AnnouncementRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class AnnouncementRemoteDataSourceImpl @Inject constructor(
    private val announcementService: AnnouncementService
) : BaseDataSource(), AnnouncementRemoteDataSource {

    override suspend fun getAllAnnouncements(page: Int): Response<AnnouncementResponse> = announcementService.getAllAnnouncements()

}
