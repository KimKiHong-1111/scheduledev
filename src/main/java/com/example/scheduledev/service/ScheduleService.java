package com.example.scheduledev.service;

import com.example.scheduledev.dto.ScheduleResponseDto;
import com.example.scheduledev.dto.ScheduleWithUsernameResponseDto;
import com.example.scheduledev.dto.UpdateScheduleRequestDto;
import com.example.scheduledev.entity.Schedule;
import com.example.scheduledev.entity.Member;
import com.example.scheduledev.repository.MemberRepository;
import com.example.scheduledev.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    //전 과제 지적사항: 레퍼지토리 끌어올때 repository로 하지말고 이름  구체적으로 지정해달라고했음.
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto save(String title, String contents, String username) {
        //멤버조회
        Member findMember = memberRepository.findMemberByUsernameOrElseThrow(username);
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
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        Member writer = findSchedule.getMember();
        return new ScheduleWithUsernameResponseDto(findSchedule.getTitle(), findSchedule.getContents(), writer.getUsername());
    }

    //수정
    @Transactional
    public ScheduleResponseDto update(Long id, UpdateScheduleRequestDto requestDto) {
//        //필수값 검증
//        if (password == null || contents == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The pw and contents are required values.");
//        }
//        validPassword(id,password);
//
//        int updatedRow = scheduleRepository.updateSchedule(id,email,title,,pw,contents);
//
//        // NPE 방지
//        if (updatedRow == 0) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist name = " + id);
//        }

        //  schedule 수정 머리에 쥐가난다.......
        // id값을 이용해 스케줄에 있는 멤버의 이메일 비밀번호를 대조해서 맞으면 업데이트한다.(구현)
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        Member member = schedule.getMember();
        if (!member.getEmail().equals(requestDto.getEmail()) || !member.getPassword().equals(requestDto.getPassword())) {
            //요기는 나중에 예외처리 로직을 넣어라!
        }
        schedule.update(requestDto.getTitle(),requestDto.getContents());
        return new ScheduleResponseDto(schedule);

    }

    //이 부분 아예 삭제하는 것이 아닌 소프트 딜리트로 변경해보기.
    public void delete(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        scheduleRepository.delete(findSchedule);
    }
}
