package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Table(name = "User")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(length = 50, nullable = false, unique = true, name = "Email")
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

//     ERD 추가
    @JsonIgnore
    @Column(name = "Activated")
    private boolean activated;

    // ERD 추가
    @ManyToMany
    @JoinTable(
            name = "UserAuthority",
            joinColumns = {@JoinColumn(name = "ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AuthorityName", referencedColumnName = "AuthorityName")})
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Inquiry> inquiryList;
}
