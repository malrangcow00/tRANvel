package com.ssafy.tranvel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StompAttractionDto {

    private String sender_id;

    private String roomId;

    private String message;

    private String latitude;

    private String longitude;
}
