package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
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
    private Long id;

    @ManyToOne
    @JsonBackReference
    private RoomHistory roomHistory;

    @OneToMany(mappedBy = "attractionGameHistory", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<AttractionImage> images;

    @OneToOne
    private AttractionList attractionList;

    @OneToOne
    @Nullable
    private RandomGame miniGameCode;

    @Column(length = 8, name = "NickName")
    @Nullable
    private String nickName;

    @Column(length = 30, name = "DateTime")
    private String dateTime;
}
