package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import com.ssafy.tranvel.data.model.AdjustmentHistoryResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdjustmentHistoryResponse(
    val result: Boolean,
    val msg: String,
    val data: List<AdjustmentHistoryResult>
) : Parcelable
