package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnnouncementDto(
    val id : Long?,
    val title : String?,
    val content : String?,
    val dateTime : String?
) : Parcelable{
    companion object{
        fun init() = AnnouncementDto(
            id = -1,
            title = null,
            content = null,
            dateTime = null
        )
    }
}
