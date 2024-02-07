package com.ssafy.tranvel.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryRequest (
    val userId : Long = 11,
):Parcelable