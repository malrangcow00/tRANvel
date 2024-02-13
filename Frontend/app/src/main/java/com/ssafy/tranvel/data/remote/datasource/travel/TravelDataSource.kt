package com.ssafy.tranvel.data.remote.datasource.travel

import com.ssafy.tranvel.data.model.dto.AdjustmentGameHistory
import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.dto.UserInfo
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface TravelDataSource {
    suspend fun createRoom(roomPassword: String): Flow<DataState<DataResponse<Room<Boolean>>>>
    suspend fun enterRoom(
        roomCode: String,
        roomPassword: String
    ): Flow<DataState<DataResponse<Room<Boolean>>>>

    suspend fun getUserList(
        roomId: Long
    ): Flow<DataState<DataResponse<List<UserInfo>>>>

    suspend fun setAdjustmentGameHistory(
        adjustmentGameHistory: RequestBody,
        image: MultipartBody.Part?
    ): Flow<DataState<DataResponse<Int>>>
}