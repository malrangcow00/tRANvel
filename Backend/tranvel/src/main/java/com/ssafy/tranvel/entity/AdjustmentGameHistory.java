package com.ssafy.tranvel.entity;
import jakarta.persistence.*;
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

    @ManyToOne
    private RoomHistory roomHistory;

    @OneToOne
    private RandomGame miniGameCode;

    @Column(length = 20, name = "TargetUser")
    private String targetUser;

    @Column(length = 30, name = "DateTime")
    private String dateTime;

    @Column(name = "Price")
    private int price;

    @Column(name = "MoneyResult")
    private int moneyResult;

    @ElementCollection
    private List<Long> selectedUsers = new ArrayList<>();

    @Column(length = 30, name = "Image")
    private String image;

    @Column(length = 3, name = "Category")
    private String category;

    @Column(length = 100, name = "Detail")
    private String detail;
}