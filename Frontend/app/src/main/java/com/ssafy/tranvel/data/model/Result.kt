package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val announcementTitle: String,
    val announcementContents: String,
    val announcementTime: String
) : Parcelable {
    companion object{
        fun create(jsonString: String): Result? {
            return try {
                Gson().fromJson(jsonString,Result::class.java)
            } catch (e: Exception){
                return null
            }
        }
    }
}
