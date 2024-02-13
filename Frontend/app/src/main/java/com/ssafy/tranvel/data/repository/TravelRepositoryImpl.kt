package com.ssafy.tranvel.data.repository

import android.util.Log
import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.dto.UserInfo
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.remote.datasource.travel.TravelDataSource
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.TravelRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class TravelRepositoryImpl @Inject constructor(
    private val travelDataSource: TravelDataSource
) : TravelRepository {
    override suspend fun createRoom(roomPassword: String): Flow<DataState<DataResponse<Room<Boolean>>>> {
        return travelDataSource.createRoom(roomPassword)
    }

    override suspend fun enterRoom(
        roomCode: String,
        roomPassword: String
    ): Flow<DataState<DataResponse<Room<Boolean>>>> {
        return travelDataSource.enterRoom(roomCode, roomPassword)
    }

    override suspend fun getUserList(roomId: Long): Flow<DataState<DataResponse<List<UserInfo>>>> {
        return travelDataSource.getUserList(roomId)
    }

    override suspend fun setAdjustmentGameHistory(
        adjustmentGameHistory: RequestBody,
        image: MultipartBody.Part?
    ): Flow<DataState<DataResponse<Int>>> {
        Log.d("TAG", "setAdjustmentGameHistory: $adjustmentGameHistory")
        return travelDataSource.setAdjustmentGameHistory(adjustmentGameHistory,image)
    }

}