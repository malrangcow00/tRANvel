package com.ssafy.tranvel.data.remote.datasource.travel.impl

import android.util.Log
import com.ssafy.tranvel.data.model.dto.AdjustmentGameHistory
import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.dto.UserInfo
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.remote.api.TravelService
import com.ssafy.tranvel.data.remote.datasource.BaseDataSource
import com.ssafy.tranvel.data.remote.datasource.travel.TravelDataSource
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class TravelDataSourceImpl @Inject constructor(
    private val travelService: TravelService
) : TravelDataSource, BaseDataSource() {
    override suspend fun createRoom(roomPassword: String): Flow<DataState<DataResponse<Room<Boolean>>>> {
        return getResult { travelService.createRoom(roomPassword) }
    }

    override suspend fun enterRoom(
        roomCode: String,
        roomPassword: String
    ): Flow<DataState<DataResponse<Room<Boolean>>>> {
        return getResult { travelService.enterRoom(roomCode, roomPassword) }
    }

    override suspend fun getUserList(roomId: Long): Flow<DataState<DataResponse<List<UserInfo>>>> {
        return getResult { travelService.getUserList(roomId) }
    }

    override suspend fun setAdjustmentGameHistory(
        adjustmentGameHistory: RequestBody,
        image: MultipartBody.Part?
    ): Flow<DataState<DataResponse<Int>>> {
        Log.d("TAG", "setAdjustmentGameHistory: $adjustmentGameHistory")
        return getResult { travelService.setAdjustmentHistory(adjustmentGameHistory, image) }
    }


}