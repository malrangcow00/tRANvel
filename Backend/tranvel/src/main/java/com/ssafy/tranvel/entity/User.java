package com.ssafy.tranvel.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(length = 50, nullable = false, unique = true, name = "Email")
    private String email;

    @Column(length = 8, nullable = false, name = "NickName")
    private String nickName;

    @Column(length = 16, nullable = false, name = "Password")
    private String password;

    @Column(name = "ProfileImage")
    @Nullable
    private String profileImage;

    @Column(name = "Balance")
    private int balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Inquiry> inquiryList;
}
