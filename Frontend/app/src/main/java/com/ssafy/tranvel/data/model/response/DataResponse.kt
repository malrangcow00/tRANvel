package com.ssafy.tranvel.data.model.response

data class DataResponse<T>(
    val result: Boolean,
    val msg: String,
    val data: T
)