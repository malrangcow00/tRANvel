package com.ssafy.tranvel.data.model.dto

data class AttractionInfo(
    val sender_id: String,
    val roomId: String,
    val name: String,
    val latitude: String,
    val longitude: String,
    val description: String,
    val city: String,
    val image: String
)