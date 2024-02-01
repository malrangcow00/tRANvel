package com.ssafy.tranvel.data.remote.datasource.announcement

import com.ssafy.tranvel.data.model.response.AnnouncementResponse
import retrofit2.Response

interface AnnouncementRemoteDataSource {
    suspend fun getAllAnnouncements(page: Int) : Response<AnnouncementResponse>
}