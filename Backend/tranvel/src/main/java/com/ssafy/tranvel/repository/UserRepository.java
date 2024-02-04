package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickName);
    Optional<User> findById(Long id);
}
