package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.AttractionGameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionGameRepository extends JpaRepository<AttractionGameHistory, Long> {
}
