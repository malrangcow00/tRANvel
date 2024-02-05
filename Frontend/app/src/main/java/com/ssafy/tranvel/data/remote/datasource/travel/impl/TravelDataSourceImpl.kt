package com.ssafy.tranvel.data.remote.datasource.travel.impl

import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.remote.api.TravelService
import com.ssafy.tranvel.data.remote.datasource.BaseDataSource
import com.ssafy.tranvel.data.remote.datasource.travel.TravelDataSource
import com.ssafy.tranvel.data.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TravelDataSourceImpl @Inject constructor(
    private val travelService: TravelService
) : TravelDataSource, BaseDataSource() {
    override suspend fun createRoom(room: Room): Flow<DataState<DataResponse<Room>>> =
        getResult { travelService.createRoom(room) }

}