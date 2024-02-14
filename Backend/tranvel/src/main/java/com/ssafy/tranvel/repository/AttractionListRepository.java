package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.AttractionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttractionListRepository extends JpaRepository<AttractionList, Long> {

    Optional<AttractionList> findByName(String name);

}
