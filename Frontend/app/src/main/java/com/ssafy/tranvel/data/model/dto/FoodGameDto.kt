package com.ssafy.tranvel.data.model.dto

data class FoodGameDto(
    val sender_id: String,
    val roomId: String,
    val selectedUserInfos: List<SelectedUserInfo>,
    val unSelectedUserInfos: List<UnSelectedUserInfo>,
)