package com.ssafy.tranvel.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRequest(
    val email: String,
    val nickName: String,
    val password: String,
    val profileImage: String?
) : Parcelable
