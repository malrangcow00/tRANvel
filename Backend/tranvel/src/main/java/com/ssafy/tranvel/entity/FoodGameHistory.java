package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodGameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private RoomHistory roomHistory;

    @OneToMany(mappedBy = "foodGameHistory", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<FoodImage> images;

    @OneToOne
    @Nullable
    private RandomGame miniGameCode;

    @ElementCollection
    @Nullable
    private List<submitUserInfo> selectedUserInfos = new ArrayList<>(); // 선택 인원은 userid와 제출음식을 묶어 저장

    @Embeddable
    @Getter
    @Setter
    public static class submitUserInfo {
        private Long userId;
        private String submittedFood;
    }

    @ElementCollection
    @Nullable
    private List<Long> unSelectedUserIds = new ArrayList<>(); // 미선택 인원은 userid만

    @ElementCollection
    @Nullable
    private List<String> foodCandidates = new ArrayList<>();

    @Column(length = 20, name = "FoodName")
    @Nullable
    private String foodName;

    @Column(length = 30, name = "DateTime")
    private String dateTime;
}
