package com.ssafy.tranvel.domain.repository

import com.ssafy.tranvel.data.model.response.AllHistoryResponse
import retrofit2.Response

interface HistoryRepository {
    suspend fun getAllHistory() : Response<AllHistoryResponse>
//    suspend fun getHistory() :
}
