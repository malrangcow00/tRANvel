package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import com.ssafy.tranvel.data.model.dto.FoodDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodHistoryResult(
    val id : Long,
    val dateTime : String?,
    val selectedUsers : List<FoodDetail>?,
    val unselectedUsers : List<Long>?,
    val foodCandidate : List<String>?,
    val foodName : String?,
    val images : List<String>?,

) : Parcelable {
    companion object{
        fun create(jsonString: String) : FoodHistoryResult?{
            return try {
                Gson().fromJson(jsonString, FoodHistoryResult::class.java)
            } catch (e : Exception){
                return null
            }
        }
    }
}