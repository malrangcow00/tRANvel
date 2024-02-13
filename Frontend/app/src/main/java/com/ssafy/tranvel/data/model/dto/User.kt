package com.ssafy.tranvel.data.model.dto

data class User(
    val id: Long,
    val email: String,
    val nickName: String,
    val profileImage: String,
    val balance: Int
)