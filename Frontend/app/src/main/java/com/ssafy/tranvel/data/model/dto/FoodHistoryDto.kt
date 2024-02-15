package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodHistoryDto(
    val id : Long,
    val dateTime : String?,
    val selectedUsers : List<FoodDetail>?,
    val unselectedUsers : List<Long>?,
    val foodCandidate : List<String>?,
    val foodName : String?,
    val images : List<String>?,
) : Parcelable {
    companion object {
        fun init() = FoodHistoryDto(
            id=-1,
            dateTime = null,
            selectedUsers = null,
            unselectedUsers = null,
            foodCandidate = null,
            foodName = null,
            images = null
        )
    }
}
