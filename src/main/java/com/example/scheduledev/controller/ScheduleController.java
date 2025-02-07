package com.example.scheduledev.controller;

import com.example.scheduledev.dto.ScheduleResponseDto;
import com.example.scheduledev.dto.ScheduleWithAgeResponseDto;
import com.example.scheduledev.dto.CreateScheduleRequestDto;
import com.example.scheduledev.dto.UpdateScheduleRequestDto;
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

    //일정표 글 등록하기
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody CreateScheduleRequestDto requestDto) {
        ScheduleResponseDto scheduleResponseDto =
                scheduleService.save(requestDto.getTitle(),requestDto.getContents(),requestDto.getUsername());
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    //일정표 목록 조회 API
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    //일정표 단건 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleWithAgeResponseDto> findById(@PathVariable Long id) {
        ScheduleWithAgeResponseDto scheduleWithAgeResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleWithAgeResponseDto,HttpStatus.OK);
    }

    //일정표 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(@PathVariable Long id, @RequestBody UpdateScheduleRequestDto requestDto) {
        ScheduleResponseDto updatedSchedule = scheduleService.update(id, requestDto);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    //일정표 삭제 API
    //소프트 딜리트 구현 시  put 으로 바꿀것.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        scheduleService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
