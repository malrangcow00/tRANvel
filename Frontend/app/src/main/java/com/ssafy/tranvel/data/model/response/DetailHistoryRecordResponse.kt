package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import com.ssafy.tranvel.data.model.DetailHistoryRecordResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailHistoryRecordResponse(
    val result: Boolean,
    val msg: String,
    val data: List<DetailHistoryRecordResult>
) : Parcelable
