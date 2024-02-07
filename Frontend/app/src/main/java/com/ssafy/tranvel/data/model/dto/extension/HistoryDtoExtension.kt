package com.ssafy.tranvel.data.model.dto.extension

import com.ssafy.tranvel.data.model.HistoryResult
import com.ssafy.tranvel.data.model.dto.HistoryDto

fun HistoryResult.toHistoryDto() = HistoryDto(
    roomid, roomName, startDate, endDate, images, balanceResult
)

fun List<HistoryResult>.toHistoryDtoList() = map { it.toHistoryDto() }