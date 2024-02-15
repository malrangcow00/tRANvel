package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import com.ssafy.tranvel.data.model.AttractionHistoryResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionHistoryResponse(
    val result: Boolean,
    val msg: String,
    val data: List<AttractionHistoryResult>
) : Parcelable
