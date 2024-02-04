package com.ssafy.tranvel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class TokenBlackList {

    @Id
    private String token;

    private Date addedDate;

    public TokenBlackList(String token) {
        this.token = token;
        this.addedDate = new Date();
    }

    // 기본 생성자, getter, setter 생략
}
