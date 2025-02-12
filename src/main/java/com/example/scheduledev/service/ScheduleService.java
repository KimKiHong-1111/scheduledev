package com.example.scheduledev.service;

import com.example.scheduledev.dto.ScheduleResponseDto;
import com.example.scheduledev.dto.ScheduleWithUsernameResponseDto;
import com.example.scheduledev.dto.UpdateScheduleRequestDto;
import com.example.scheduledev.entity.Schedule;
import com.example.scheduledev.entity.Member;
import com.example.scheduledev.repository.MemberRepository;
import com.example.scheduledev.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    //전 과제 지적사항: 레퍼지토리 끌어올때 repository로 하지말고 이름  구체적으로 지정해달라고했음.
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    //일정등록
    public ScheduleResponseDto save(Long memberId, String title, String contents, String username) {
        //멤버조회
        //세션에 id가 저장되어있다
        if (!memberRepository.existsById(memberId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 회원을 찾을 수 없습니다.");
        }
        Member findMember = memberRepository.getReferenceById(memberId);
        //findMember 객체를 다 넣는 것.
        Schedule schedule = new Schedule(findMember, title, contents);
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents());
    }

    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll().stream().map(com.example.scheduledev.dto.ScheduleResponseDto::toDto).toList();
    }

    //나이가 아니라 다른요소 추가 필요
    //유저명,제목,할일
    public ScheduleWithUsernameResponseDto findById(Long id) {
        Schedule findSchedule = scheduleRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"수정할 수 없습니다."));
        Member writer = findSchedule.getMember();
        return new ScheduleWithUsernameResponseDto(findSchedule.getTitle(), findSchedule.getContents(), writer.getUsername());
    }

    //수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto requestDto) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"수정할 수 없습니다."));
        Member member = schedule.getMember();
        if (!member.getEmail().equals(requestDto.getEmail()) || !member.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"일정은 작성자만 수정이 가능합니다.");
        }
        schedule.update(requestDto.getTitle(),requestDto.getContents());
        return new ScheduleResponseDto(schedule);
    }

    //삭제
    public void delete(Long id) {
        Schedule findSchedule = scheduleRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"수정할 수 없습니다."));
        scheduleRepository.delete(findSchedule);
    }
}
