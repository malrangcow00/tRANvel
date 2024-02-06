package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryDto(
    val roomId: Int,
    val period : String,
    val images : List<String>?,
    val roomName : String,
    val profit : Int?
) : Parcelable {
    companion object{
        fun init() = HistoryDto(
            roomId = -1,
            period = "",
            images = null,
            roomName = "",
            profit = null
        )
    }
}