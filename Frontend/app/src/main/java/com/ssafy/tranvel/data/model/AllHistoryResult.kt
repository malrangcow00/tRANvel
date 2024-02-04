package com.ssafy.tranvel.data.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllHistoryResult(

) : Parcelable{
    companion object{
        fun create(jsonString : String) : AllHistoryResult?{
            return try {
                Gson().fromJson(jsonString, AllHistoryResult::class.java)
            } catch (e : Exception){
                return null
            }
        }
    }
}
