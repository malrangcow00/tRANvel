package com.ssafy.tranvel.data.model.dto.extension

import com.ssafy.tranvel.data.model.DetailHistoryRecordResult
import com.ssafy.tranvel.data.model.AdjustmentHistoryResult
import com.ssafy.tranvel.data.model.AttractionHistoryResult
import com.ssafy.tranvel.data.model.FoodHistoryResult
import com.ssafy.tranvel.data.model.HistoryResult
import com.ssafy.tranvel.data.model.dto.AdjustmentHistoryDto
import com.ssafy.tranvel.data.model.dto.AttractionHistoryDto
import com.ssafy.tranvel.data.model.dto.DetailHistoryRecordDto
import com.ssafy.tranvel.data.model.dto.FoodHistoryDto
import com.ssafy.tranvel.data.model.dto.HistoryDto

fun HistoryResult.toHistoryDto() = HistoryDto(
    roomid, roomName, startDate, endDate, images, balanceResult
)

fun List<HistoryResult>.toHistoryDtoList() = map { it.toHistoryDto() }

fun AdjustmentHistoryResult.toAdjustmentHistoryDto() = AdjustmentHistoryDto(
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
fun List<AdjustmentHistoryResult>.toAdjustmentHistoryDtoList() = map { it.toAdjustmentHistoryDto() }

fun AttractionHistoryResult.toAttractionHistoryDto() = AttractionHistoryDto(
    id, dateTime, nickName, images, attractionList
)
fun List<AttractionHistoryResult>.toAttractionHistoryList() = map { it.toAttractionHistoryDto() }

fun FoodHistoryResult.toFoodHistoryDto() = FoodHistoryDto(
    id, dateTime, selectedUsers, unselectedUsers, foodCandidate, foodName, images
)
fun List<FoodHistoryResult>.toFoodHistoryList() = map { it.toFoodHistoryDto() }

fun DetailHistoryRecordResult.toDetailHistoryRecordDto() = DetailHistoryRecordDto(
    contentId, historyCategory, dateTime, images, detail, latitude, longitude, moneyResult
)

fun List<DetailHistoryRecordResult>.toDetailHistoryRecordDto() =
    map { it.toDetailHistoryRecordDto() }
