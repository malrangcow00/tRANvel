package com.ssafy.tranvel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDto {

    @NotNull
    private String email;

    @NotNull
    private String password;

}
