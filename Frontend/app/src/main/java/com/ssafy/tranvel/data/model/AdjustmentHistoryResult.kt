package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdjustmentHistoryResult(
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
        fun create(jsonString: String) : AdjustmentHistoryResult?{
            return try {
                Gson().fromJson(jsonString, AdjustmentHistoryResult::class.java)
            } catch (e : Exception){
                return null
            }
        }
    }
}
