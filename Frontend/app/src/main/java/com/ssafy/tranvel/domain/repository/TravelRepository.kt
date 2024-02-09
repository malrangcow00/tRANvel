package com.ssafy.tranvel.domain.repository

import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow

interface TravelRepository {
    suspend fun createRoom(roomPassword: String): Flow<DataState<DataResponse<Room<Boolean>>>>

    suspend fun enterRoom(
        roomCode: String,
        roomPassword: String
    ): Flow<DataState<DataResponse<Room<Boolean>>>>
}