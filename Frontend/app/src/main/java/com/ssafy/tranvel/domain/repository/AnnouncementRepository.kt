package com.ssafy.tranvel.domain.repository

import com.ssafy.tranvel.data.model.dto.AnnouncementDto
import com.ssafy.tranvel.data.model.response.AnnouncementResponse
import retrofit2.Response

interface AnnouncementRepository {
    suspend fun getAllAnnouncement(page: Int) : Response<AnnouncementResponse>
}