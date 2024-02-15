package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import com.ssafy.tranvel.data.model.dto.AttractionDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionHistoryResult(
    val id : Long,
    val dateTime : String?,
    val nickName : String?,
    val images : List<String>?,
    val attractionList :AttractionDetail,
) : Parcelable {
    companion object{
        fun create(jsonString: String) : AttractionHistoryResult?{
            return try {
                Gson().fromJson(jsonString, AttractionHistoryResult::class.java)
            } catch (e : Exception){
                return null
            }
        }
    }
}
