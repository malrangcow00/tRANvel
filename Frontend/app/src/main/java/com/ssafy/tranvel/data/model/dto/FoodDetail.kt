package com.ssafy.tranvel.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodDetail(
    val userId : Long?,
    val submittedFood : String?,
) : Parcelable {
    companion object {
        fun init() = FoodDetail(
            userId = null,
            submittedFood = null

        )
    }
}