package com.ssafy.tranvel.service;


import com.ssafy.tranvel.dto.AnnouncementDto;
import com.ssafy.tranvel.entity.Announcement;
import com.ssafy.tranvel.repository.AnnouncementRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Announcement createAnnouncement(AnnouncementDto announcementDto) {

        Announcement announcement = Announcement.builder()
                .title(announcementDto.getTitle())
                .content(announcementDto.getContent())
                .datetime(LocalDateTime.now().toString())
                .build();

        announcementRepository.save(announcement);

        return announcement;
    }


    public Announcement findAnnouncement(int announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId).get();
        return announcement;

    }

    public Announcement updateAnnouncement(int announcementId, AnnouncementDto announcementDto) {
        Announcement announcement = announcementRepository.findById(announcementId).get();
        announcement.update(announcementDto.getTitle(), announcementDto.getContent());
        announcementRepository.save(announcement);
        return announcement;
    }

    public void deleteAnnouncement(int announcementId) {
        announcementRepository.deleteById(announcementId);
    }

}
