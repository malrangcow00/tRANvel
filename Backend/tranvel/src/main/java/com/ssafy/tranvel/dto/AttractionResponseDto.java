package com.ssafy.tranvel.dto;


import com.ssafy.tranvel.entity.AttractionList;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Getter
@Builder
public class AttractionResponseDto {

    private Long id;

    private String dateTime;

    private String nickName;

    private List<String> images;

    private AttractionList attractionList;


}
