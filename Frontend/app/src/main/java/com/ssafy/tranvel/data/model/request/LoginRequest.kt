package com.ssafy.tranvel.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginRequest(
    val email: String,
    val nickName:String = "String",
    val password: String,
    val profileImage:String?,
    val balance: Int?
) : Parcelable