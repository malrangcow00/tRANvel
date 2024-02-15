package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import com.ssafy.tranvel.data.model.FoodHistoryResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodHistoryResponse(
    val result: Boolean,
    val msg: String,
    val data: List<FoodHistoryResult>
) : Parcelable
