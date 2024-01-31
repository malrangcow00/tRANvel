package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByNickName(String nickName);
    Optional<User> findOneWithAuthoritiesByEmail(String email);
}
