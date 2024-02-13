package com.ssafy.tranvel.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StompAttractionResponseDto {

    private String sender_id;

    private String roomId;

    private String name; // 관광지명

    private String latitude;

    private String longitude;

    private String description; // 관광지소개

    private String city; // 제공기관명

    private String image;

}
