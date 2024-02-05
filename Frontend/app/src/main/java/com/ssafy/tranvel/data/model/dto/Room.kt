package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val userId: Long = 11,
    val roomCode: String = " ",
    val roomName: String = " ",
    val roomPassword: String,
    val startDate: String = " ",
    val endDate: String = " ",
    val balanceResult: Int = 0,
    val roomId: Long = 0
) : Parcelable