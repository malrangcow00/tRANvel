package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionDetail(
    val id : Long?,

    @SerializedName("image")
    val image : String?,

    @SerializedName("관광지명")
    val attrName : String?,
    
    @SerializedName("위도")
    val latitude: Double?,

    @SerializedName("경도")
    val longitude: Double?,

    @SerializedName("관광지소개")
    val attrInfo : String?,

    @SerializedName("제공기관명")
    val orgName : String?
) : Parcelable {
    companion object {
        fun init() = AttractionDetail(
            id = -1,
            image = null,
            attrName = "",
            latitude = -1.0,
            longitude = -1.0,
            attrInfo = "",
            orgName = ""
        )
    }
}