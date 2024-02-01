package com.ssafy.tranvel.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoModifyRequest(
    val nickName: String,
    val password: String,
    val profileImg: String?,
) : Parcelable