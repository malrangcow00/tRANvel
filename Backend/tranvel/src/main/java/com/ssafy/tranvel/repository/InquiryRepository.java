package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

    Optional<Inquiry> findByUser_Id(int userId);
}
