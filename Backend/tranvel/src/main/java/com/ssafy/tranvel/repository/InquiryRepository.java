package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

    Optional<Inquiry> findByUser_Id(int userId);
}
