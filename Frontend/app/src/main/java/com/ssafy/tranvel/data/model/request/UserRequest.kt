package com.ssafy.tranvel.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRequest(
    val email: String,
    val nickName: String? = null,
    val password: String,
) : Parcelable
