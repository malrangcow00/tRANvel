package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.AnswerDto;
import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.entity.Answer;
import com.ssafy.tranvel.service.AnswerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter @Setter
@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")

public class AnswerController {

    private ResponseDto response;
    private final AnswerService answerService;

    @PostMapping("/")
    public ResponseEntity<ResponseDto> postAnswer(@RequestBody @Validated
                                                  AnswerDto answerDto) {
        Answer answer = answerService.createAnswer(answerDto);
        response = new ResponseDto(true, "답변이 정상적으로 등록되었습니다.", answer);

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
