package com.ssafy.tranvel.data.model.dto.extension

import com.ssafy.tranvel.data.model.HistoryResult
import com.ssafy.tranvel.data.model.dto.HistoryDto

fun HistoryResult.toHistoryDto() = HistoryDto(
    roomId, period, images, roomName, profit
)

fun List<HistoryResult>.toHistoryDtoList() = map { it.toHistoryDto() }