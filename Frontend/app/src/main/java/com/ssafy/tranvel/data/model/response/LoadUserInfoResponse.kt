package com.ssafy.tranvel.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class LoadUserInfoResponse (
    val result : Boolean,
    val nickname : String,
    val profileImage : String
) : Parcelable