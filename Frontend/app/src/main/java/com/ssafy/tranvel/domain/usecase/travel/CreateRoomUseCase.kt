package com.ssafy.tranvel.domain.usecase.travel

import android.util.Log
import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.TravelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateRoomUseCase @Inject constructor(
    private val travelRepository: TravelRepository
) {
    suspend fun execute(password:String): Flow<DataState<DataResponse<Room>>> {
        Log.d("TAG", "execute: ${Room(roomPassword = password)}")
        return travelRepository.createRoom(Room(roomPassword = password))
    }
}