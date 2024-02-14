package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailHistoryRecordResult(
    val contentId : Long,
    val historyCategory : String,
    val dateTime : String?,
    val images : List<String>?,
    val detail : String?,
    val moneyResult : Long?
) : Parcelable {
    companion object{
        fun create(jsonString: String) : DetailHistoryRecordResult?{
            return try {
                Gson().fromJson(jsonString, DetailHistoryRecordResult::class.java)
            } catch (e : Exception){
                return null
            }
        }
    }
}
