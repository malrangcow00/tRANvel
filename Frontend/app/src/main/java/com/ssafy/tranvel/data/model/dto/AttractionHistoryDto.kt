package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionHistoryDto(
    val id : Long,
    val dateTime : String?,
    val nickName : String?,
    val images : List<String>?,
    val attractionList :AttractionDetail?,
) : Parcelable {
    companion object {
        fun init() = AttractionHistoryDto(
            id = -1,
            dateTime = null,
            nickName = null,
            images = null,
            attractionList = null
        )
    }
}
