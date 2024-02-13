package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Room<T>(
    val roomId:Long,
    val roomCode: String,
    val roomPassword: String,
    val authority:T
)