package com.ssafy.tranvel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StompFoodSubmitDto {

    private String sender_id;

    private String roomId;

    private String foodGameHistoryId;

    private String food;
}
