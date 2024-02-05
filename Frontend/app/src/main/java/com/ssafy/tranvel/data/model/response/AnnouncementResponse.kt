package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import com.ssafy.tranvel.data.model.AnnouncementResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnnouncementResponse(
    val result : Boolean,
    val msg : String,
    val data : List<AnnouncementResult>
) : Parcelable
