package com.ssafy.tranvel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto<T> {

    private boolean result; // 성공 여부
    private String msg; // 결과 메시지
    private T data; // 결과 데이터

}
