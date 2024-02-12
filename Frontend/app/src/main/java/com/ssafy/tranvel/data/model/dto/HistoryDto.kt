package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryDto(
    val roomid: Long,
    val roomName : String?,
    val startDate : String,
    val endDate : String?,
    val images: List<String>?,
    val balanceResult : Int,
) : Parcelable {
    companion object {
        fun init() = HistoryDto(
            roomid = -1,
            roomName = "",
            startDate = "",
            endDate = null,
            balanceResult = 0,
            images = null,
        )
    }
}
