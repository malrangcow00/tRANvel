package com.ssafy.tranvel.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StompFoodGameStartDto {

    private String sender_id;

    private String roomId;

    private Float randFloat;

    private Long randLong;
}
