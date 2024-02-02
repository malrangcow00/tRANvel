package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataResponse<T : Parcelable>(
    val result: Boolean,
    val msg: String,
    val data: T
) : Parcelable