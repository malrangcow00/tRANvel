package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdjustmentHistoryDto(
    val id: Long,
    val miniGameCode: String?,
    val targetUser: String?,
    val dateTime: String?,
    val price: Long,
    val moneyResult: Long,
    val selectedUsers: List<Long>?,
    val images: List<String>?,
    val category: String?,
    val detail: String?,
    val location: String?
) : Parcelable {
    companion object {
        fun init() = AdjustmentHistoryDto(
            id = -1,
            miniGameCode = null,
            targetUser = null,
            dateTime = null,
            price = 0,
            moneyResult = 0,
            selectedUsers = null,
            images = null,
            category = null,
            detail = null,
            location = null
        )
    }
}
