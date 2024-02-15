package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionDetail(
    val id : Long?,
    val images : List<String>?,
    val attrName : String?,
    val latitude: Double?,
    val longitude: Double?,
    val attrInfo : String?,
    val orgName : String?
) : Parcelable {
    companion object {
        fun init() = AttractionDetail(
            id = -1,
            images = null,
            attrName = null,
            latitude = null,
            longitude = null,
            attrInfo = null,
            orgName = null
        )
    }
}