package com.ssafy.tranvel.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmailAuthRequest(
    val verificationCode: String,
    val email: String
) : Parcelable
