package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    User findByNickName(String nickName);
    Optional<User> findOneWithAuthoritiesByEmail(String email);
}
