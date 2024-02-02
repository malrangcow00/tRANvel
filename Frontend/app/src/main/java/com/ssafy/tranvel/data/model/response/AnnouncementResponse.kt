package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import com.ssafy.tranvel.data.model.Result
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnnouncementResponse(
    val info : InfoResponse,
    val results : List<Result>
) : Parcelable
