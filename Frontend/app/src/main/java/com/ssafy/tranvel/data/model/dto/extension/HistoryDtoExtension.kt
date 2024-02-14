package com.ssafy.tranvel.data.model.dto.extension

import com.ssafy.tranvel.data.model.DetailHistoryRecordResult
import com.ssafy.tranvel.data.model.DetailHistoryResult
import com.ssafy.tranvel.data.model.HistoryResult
import com.ssafy.tranvel.data.model.dto.DetailHistoryDto
import com.ssafy.tranvel.data.model.dto.DetailHistoryRecordDto
import com.ssafy.tranvel.data.model.dto.HistoryDto

fun HistoryResult.toHistoryDto() = HistoryDto(
    roomid, roomName, startDate, endDate, images, balanceResult
)

fun List<HistoryResult>.toHistoryDtoList() = map { it.toHistoryDto() }

fun DetailHistoryResult.toDetailHistoryDto() = DetailHistoryDto(
    id,
    miniGameCode,
    targetUser,
    dateTime,
    price,
    moneyResult,
    selectedUsers,
    images,
    category,
    detail,
    location
)

fun List<DetailHistoryResult>.toDetailHistoryDtoList() = map { it.toDetailHistoryDto() }

fun DetailHistoryRecordResult.toDetailHistoryRecordDto() = DetailHistoryRecordDto(
    contentId, historyCategory, dateTime, images, detail, latitude, longitude, moneyResult
)

fun List<DetailHistoryRecordResult>.toDetailHistoryRecordDto() =
    map { it.toDetailHistoryRecordDto() }
