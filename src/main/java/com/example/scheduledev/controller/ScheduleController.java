package com.example.scheduledev.controller;

import com.example.scheduledev.dto.ScheduleResponseDto;
import com.example.scheduledev.dto.ScheduleWithUsernameResponseDto;
import com.example.scheduledev.dto.CreateScheduleRequestDto;
import com.example.scheduledev.dto.UpdateScheduleRequestDto;
import com.example.scheduledev.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    //하나하나씩 테스트 해보기.

    //일정 생성
    @PostMapping("/create")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody CreateScheduleRequestDto requestDto) {
        // memberId는 어디서 가져오는지 확인 필요 (세션, JWT 등)
        Long memberId = 1L; // 임시 값 (실제 서비스에서는 로그인 정보를 활용해야 함)

        ScheduleResponseDto response = scheduleService.save(memberId, requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername());
        return ResponseEntity.ok(response);
    }
    //일정표 목록 조회 API
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    //일정표 단건 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleWithUsernameResponseDto> findById(@PathVariable Long id) {
        ScheduleWithUsernameResponseDto scheduleWithUsernameResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleWithUsernameResponseDto,HttpStatus.OK);
    }

    //일정표 수정 API 구현하고싶다!! 요거만하면 일단 lv4는 끝날거같다.
    //
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(@PathVariable Long id, @RequestBody UpdateScheduleRequestDto requestDto) {
        ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(id, requestDto);
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
