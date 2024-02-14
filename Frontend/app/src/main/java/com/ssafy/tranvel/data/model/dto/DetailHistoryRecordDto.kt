package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailHistoryRecordDto(
    val contentId: Long,
    val historyCategory: String,
    val dateTime: String?,
    val images: List<String>?,
    val detail: String?, //여기서는 detail이 장소
    val moneyResult: Long?
) : Parcelable {
    companion object {
        fun init() = DetailHistoryRecordDto(
            contentId = -1,
            historyCategory = "",
            dateTime = null,
            images = null,
            detail = null,
            moneyResult = 0
        )
    }
}
