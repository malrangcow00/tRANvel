package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
