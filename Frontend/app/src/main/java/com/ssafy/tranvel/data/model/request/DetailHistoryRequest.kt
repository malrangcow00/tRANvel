package com.ssafy.tranvel.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailHistoryRequest(
    val roomId : Long
) : Parcelable
