package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import com.ssafy.tranvel.data.model.AnnouncementResult
import com.ssafy.tranvel.data.model.HistoryResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryResponse(
    val result: Boolean,
    val msg: String,
    val data: List<HistoryResult>
) : Parcelable
