package com.ssafy.tranvel.data.remote.api

import com.ssafy.tranvel.data.model.response.AnnouncementResponse
import retrofit2.Response
import retrofit2.http.GET

interface AnnouncementService {

    @GET("announcement")
    suspend fun getAllAnnouncements(): Response<AnnouncementResponse>
}
