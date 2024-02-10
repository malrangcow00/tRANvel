package com.ssafy.tranvel.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StompFoodGameDto {

    private String sender_id;

    private String roomId;

    private List<String> selectedUserNicknames;

    private List<String> unSelectedUserNicknames;

    private List<String> foodCandidates;

}
