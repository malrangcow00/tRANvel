package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.AnnouncementDto;
import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.entity.Announcement;
import com.ssafy.tranvel.service.AnnouncementService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Getter @Setter
@RequiredArgsConstructor
@RestController
@RequestMapping("/announcement")
public class AnnounceController {

    private ResponseDto response;
    private final AnnouncementService announcementService;


    @GetMapping()
    public ResponseEntity<ResponseDto> getAnnouncement() {
//    public List<Announcement> getAnnouncement() {
        List<Announcement> announcementList = announcementService.getAllAnnouncement();
        if (announcementList.isEmpty()) {
            response = new ResponseDto(false, "작성된 공지사항이 없습니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response = new ResponseDto(true, "공지사항 전체 조회", announcementList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
//        return announcementList;
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> postAnnouncement(@RequestBody @Validated AnnouncementDto announcementDto){
        Announcement announcement = announcementService.createAnnouncement(announcementDto);
        response = new ResponseDto(true, "공지사항이 등록되었습니다.", announcement.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{announcement-id}")
    public ResponseEntity<ResponseDto> detailAnnouncement(@PathVariable("announcement-id") Long announcementId) {
        Announcement announcement = announcementService.findAnnouncement(announcementId);
        response = new ResponseDto(true, "공지사항 조회.", announcement);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{announcement-id}")
    public ResponseEntity<ResponseDto> putAnnouncement(@PathVariable("announcement-id") Long announcementId,
                                                       @RequestBody @Validated
                                                       AnnouncementDto announcementDto) {
        Announcement announcement = announcementService.updateAnnouncement(announcementId, announcementDto);
        response = new ResponseDto(true, "공지사항이 수정되었습니다.", announcement);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{announcement-id}")
    public ResponseEntity<ResponseDto> deleteAnnouncement(@PathVariable("announcement-id") Long announcementId) {
        response = new ResponseDto(true, "공지사항이 삭제되었습니다.", null);
        announcementService.deleteAnnouncement(announcementId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}