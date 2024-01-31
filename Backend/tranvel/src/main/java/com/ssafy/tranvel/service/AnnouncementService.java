package com.ssafy.tranvel.service;


import com.ssafy.tranvel.entity.Announcement;
import com.ssafy.tranvel.repository.AnnouncementRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public List<Announcement> getAllAnnouncement() {

        return this.announcementRepository.findAll();
    }

}
