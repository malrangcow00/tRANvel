package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByNickName(String nickName);
    Optional<User> findOneWithAuthoritiesByEmail(String email);
}
