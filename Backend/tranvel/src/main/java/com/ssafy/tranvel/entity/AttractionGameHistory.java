package com.ssafy.tranvel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionGameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private RoomHistory roomHistory;

    @OneToMany(mappedBy = "attractionGameHistory")
    private List<AttractionImage> attractionImages;

    @OneToOne
    private AttractionList attractionList;

    @OneToOne
    private RandomGame miniGameCode;

    @Column(length = 8, name = "NickName")
    private String nickName;

    @Column(length = 30, name = "DateTime")
    private String dateTime;
}
