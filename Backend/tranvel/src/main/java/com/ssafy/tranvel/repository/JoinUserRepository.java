package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.JoinUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinUserRepository extends JpaRepository<JoinUser, Long> {

    Optional<List<JoinUser>> findAllByUserId(Long userId);
}
