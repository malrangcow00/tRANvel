package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Table(name = "User")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(length = 50, nullable = false, name = "Email")
    private String email;

    @JsonIgnore
    @Column(nullable = false, name = "Password")
    private String password;

    @Column(length = 8, nullable = false, name = "NickName")
    private String nickName;

    @Column(name = "ProfileImage")
    @Nullable
    private String profileImage;

    @Column(name = "Balance")
    private int balance;

    // ERD 수정
    @JsonIgnore
    @Column(name = "Activated")
    private boolean activated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Inquiry> inquiryList;
}
