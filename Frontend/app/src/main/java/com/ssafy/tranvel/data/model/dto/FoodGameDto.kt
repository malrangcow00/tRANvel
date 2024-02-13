package com.ssafy.tranvel.data.model.dto

data class FoodGameDto(
    val sender_id: String,
    val roomId: String,
    val selectedUserProfileImages: List<String>,
    val unSelectedUserProfileImages: List<String>,
    val foodCandidates: List<String>
)