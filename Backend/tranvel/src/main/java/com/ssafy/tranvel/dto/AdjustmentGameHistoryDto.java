package com.ssafy.tranvel.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class AdjustmentGameHistoryDto {

    private Long roomId;

    private int price;

    private List<Long> selectedUsers; // JoinUser의 userId를 받음

    private MultipartFile image;

    private String category;

    private String detail;
}
