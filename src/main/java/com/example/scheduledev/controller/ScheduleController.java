package com.example.scheduledev.controller;

import com.example.scheduledev.dto.ScheduleResponseDto;
import com.example.scheduledev.dto.ScheduleWithAgeResponseDto;
import com.example.scheduledev.dto.CreateScheduleRequestDto;
import com.example.scheduledev.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    //게시판 글 등록하기
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody CreateScheduleRequestDto requestDto) {
        ScheduleResponseDto scheduleResponseDto =
                scheduleService.save(requestDto.getTitle(),requestDto.getContents(),requestDto.getUsername());
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    //게시판 목록 조회 API
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> boardResponseDtoList = scheduleService.findAll();

        return new ResponseEntity<>(boardResponseDtoList, HttpStatus.OK);
    }

    //게시판 단건 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleWithAgeResponseDto> findById(@PathVariable Long id) {
        ScheduleWithAgeResponseDto scheduleWithAgeResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleWithAgeResponseDto,HttpStatus.OK);
    }

    //게시판 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        scheduleService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
