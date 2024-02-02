package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoResponse(
    val count: Int?,
    val pages: Int?,
    val next: String?,
    val prev: String?,
) : Parcelable