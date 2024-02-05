package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenDto(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
) : Parcelable