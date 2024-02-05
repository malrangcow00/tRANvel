package com.ssafy.tranvel.data.remote.datasource.history

import com.ssafy.tranvel.data.model.response.HistoryResponse
import retrofit2.Response

interface HistoryRemoteDataSource {
    suspend fun getAllHistories() : Response<HistoryResponse>
}