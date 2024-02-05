package com.ssafy.tranvel.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;


    @Column(name = "Profit")
    private int profit;


    // 1 == 방장, 0 == 일반 참여자
    @Column(name = "Authority")
    private boolean authority;


//    @ManyToOne
//    @JsonBackReference
//    private User user;


    @ManyToOne
    @JsonBackReference
    private RoomHistory roomHistory;

}