package com.ssafy.tranvel.dto;

import com.ssafy.tranvel.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String nickName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    private String password;

//    private String profileImage;

//    private int balance;

    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setNickName(user.getNickName());
        dto.setPassword(user.getPassword());
//        dto.setProfileImage(user.getProfileImage());
//        dto.setBalance(user.getBalance());
        return dto;
    }
}
