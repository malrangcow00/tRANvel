package com.ssafy.tranvel.data.model.dto.extension

import com.ssafy.tranvel.data.model.AnnouncementResult
import com.ssafy.tranvel.data.model.dto.AnnouncementDto

fun AnnouncementResult.toAnnouncementDto() = AnnouncementDto(
    id, title, content, dateTime
)

fun List<AnnouncementResult>.toAnnouncementDtoList() = map { it.toAnnouncementDto() }
