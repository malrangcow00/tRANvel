package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import com.ssafy.tranvel.data.model.DetailHistoryResult
import com.ssafy.tranvel.data.model.HistoryResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailHistoryResponse(
    val result: Boolean,
    val msg: String,
    val data: List<DetailHistoryResult>
) : Parcelable
