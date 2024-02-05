package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.remote.datasource.travel.TravelDataSource
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.TravelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TravelRepositoryImpl @Inject constructor(
    private val travelDataSource: TravelDataSource
) : TravelRepository {
    override suspend fun createRoom(room: Room): Flow<DataState<DataResponse<Room>>> =
        flow {
            emitAll(travelDataSource.createRoom(room))
        }
}