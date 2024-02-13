package com.ssafy.tranvel.data.model.dto

data class AdjustmentGameHistory(
    val roomId: Long,
    val price: Int,
    val selectedUsers: List<Long>,
    val category: String,
    val detail: String,
    val location: String,
)
