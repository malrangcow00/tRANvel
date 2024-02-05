package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnnouncementResult(
    val id : Long,
    val title : String,
    val content : String,
    val dateTime : String
) : Parcelable {
    companion object{
        fun create(jsonString : String) : AnnouncementResult?{
            return try{
                Gson().fromJson(jsonString,AnnouncementResult::class.java)
            } catch (e:Exception){
                return null
            }
        }
    }
}
