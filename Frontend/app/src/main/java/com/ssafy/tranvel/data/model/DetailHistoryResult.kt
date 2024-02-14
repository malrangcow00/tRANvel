package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailHistoryResult(
    val id : Long,
    val miniGameCode : String?,
    val targetUser : String?,
    val dateTime : String?,
    val price : Long,
    val moneyResult : Long,
    val selectedUsers : List<Long>?,
    val images : List<String>?,
    val category : String?,
    val detail : String?,
    val location : String?

) : Parcelable {
    companion object{
        fun create(jsonString: String) : DetailHistoryResult?{
            return try {
                Gson().fromJson(jsonString, DetailHistoryResult::class.java)
            } catch (e : Exception){
                return null
            }
        }
    }
}
