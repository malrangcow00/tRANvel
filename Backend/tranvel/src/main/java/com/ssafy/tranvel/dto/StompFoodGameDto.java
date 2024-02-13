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

    private List<selectedUserInfo> selectedUserInfos;

    private List<unSelectedUserInfo> unSelectedUserInfos;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class selectedUserInfo {
        private String nickname;
        private String profileImage;
        private String submittedFood;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class unSelectedUserInfo {
        private String nickname;
        private String profileImage;
    }
}
