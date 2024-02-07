package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryResult(
    val roomid: Long,
    val roomName : String,
    val startDate : String,
    val endDate : String?,
    val images: String?,
    val balanceResult : Int,
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
