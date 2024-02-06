package com.ssafy.tranvel.data.remote.datasource.travel

import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow

interface TravelDataSource {
    suspend fun createRoom(room:Room): Flow<DataState<DataResponse<Room>>>
}