package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmailAuthResponse(
    val result: Boolean,
    val msg: String
) : Parcelable