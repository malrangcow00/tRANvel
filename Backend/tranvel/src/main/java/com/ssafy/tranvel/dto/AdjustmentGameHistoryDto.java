package com.ssafy.tranvel.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdjustmentGameHistoryDto {

    private Long id;

    private Long roomId;

    private Long miniGameCodeId;

    private String targetUser;

    private String dateTime;

    private int price;

    private List<Long> selectedUsers; // JoinUser의 userId를 받음

    private String image;

    private String category;

    private String detail;
}
