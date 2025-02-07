package com.example.scheduledev.service;

import com.example.scheduledev.dto.ScheduleResponseDto;
import com.example.scheduledev.dto.ScheduleWithAgeResponseDto;
import com.example.scheduledev.entity.Schedule;
import com.example.scheduledev.entity.Member;
import com.example.scheduledev.repository.MemberRepository;
import com.example.scheduledev.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        Schedule schedule = new Schedule(title,contents);
        schedule.setMember(findMember);

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents());
    }

    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll().stream().map(com.example.scheduledev.dto.ScheduleResponseDto::toDto).toList();

    }

    //나이가 아니라 다른요소 추가 필요
    //유저명,제목,할일
    public ScheduleWithAgeResponseDto findById(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        Member writer = findSchedule.getMember();
        return new ScheduleWithAgeResponseDto(findSchedule.getTitle(), findSchedule.getContents(), writer.getUsername());
    }


    //이 부분 아예 삭제하는 것이 아닌 소프트 딜리트로 변경해보기.
    public void delete(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        scheduleRepository.delete(findSchedule);
    }
}
