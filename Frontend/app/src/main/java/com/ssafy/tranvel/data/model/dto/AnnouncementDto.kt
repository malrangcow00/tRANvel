package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnnouncementDto(
    val title : String?,
    val contents : String?,
    val time : String?
) : Parcelable{
    companion object{
        fun init() = AnnouncementDto(
            title = null,
            contents = null,
            time = null
        )
    }
}
