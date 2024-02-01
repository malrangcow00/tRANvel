package com.ssafy.tranvel.data.model.dto.extension

import com.ssafy.tranvel.data.model.Result
import com.ssafy.tranvel.data.model.dto.AnnouncementDto

fun Result.toAnnouncementDto() = AnnouncementDto(
    title, content, time
)

fun List<Result>.toAnnouncementDtoList() = map { it.toAnnouncementDto() }