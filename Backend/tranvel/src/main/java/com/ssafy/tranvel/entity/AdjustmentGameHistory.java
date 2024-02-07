package com.ssafy.tranvel.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdjustmentGameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    private RoomHistory roomHistory;

    @OneToOne
    @Nullable
    private RandomGame miniGameCode;

    @Column(length = 20, name = "TargetUser")
    @Nullable
    private String targetUser;

    @Column(length = 30, name = "DateTime")
    @Nullable
    private String dateTime;

    @Column(name = "Price")
    private int price;

    @Column(name = "MoneyResult")
    private int moneyResult;

    @ElementCollection
    private List<Long> selectedUsers = new ArrayList<>();

    @Column(length = 30, name = "Image")
    @Nullable
    private String image;

    @Column(length = 3, name = "Category")
    @Nullable
    private String category;

    @Column(length = 100, name = "Detail")
    @Nullable
    private String detail;
}