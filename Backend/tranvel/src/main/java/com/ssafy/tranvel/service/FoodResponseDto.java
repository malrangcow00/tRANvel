package com.ssafy.tranvel.service;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FoodResponseDto {

    private Long id;

    private String dateTime;

    private List<Long> selectedUsers;

    private List<Long> unselectedUsers;

    private List<String> foodCandidates;

    private String foodName;

    private List<String> images;

}
