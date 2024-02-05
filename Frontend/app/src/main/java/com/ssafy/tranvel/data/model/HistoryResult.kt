package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryResult(
    val roomId: Int,
    val period : String,
    val images : List<String>?,
    val roomName : String,
    val profit : Int?

) : Parcelable{
    companion object{
        fun create(jsonString : String) : HistoryResult?{
            return try {
                Gson().fromJson(jsonString, HistoryResult::class.java)
            } catch (e : Exception){
                return null
            }
        }
    }
}
