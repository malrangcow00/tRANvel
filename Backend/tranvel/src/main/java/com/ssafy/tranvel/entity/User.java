package com.ssafy.tranvel.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
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

    @Column(nullable = false, name = "Password")
    private String password;

    @Column(name = "ProfileImage")
    private int profileImage;

    @Column(name = "Balance")
    private int balance;

}
