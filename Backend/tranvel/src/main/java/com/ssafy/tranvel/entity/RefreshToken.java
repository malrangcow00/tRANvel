package com.ssafy.tranvel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;;
import jakarta.persistence.Id;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    private String token;

    private String username;

    // 기본 생성자
    public RefreshToken() {
    }

    // 매개변수를 받는 생성자
    public RefreshToken(String username, String token) {
        this.username = username;
        this.token = token;
    }

    // getter 및 setter 메서드
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
