package com.example.scheduledev.service;

import com.example.scheduledev.dto.CommentResponseDto;
import com.example.scheduledev.entity.Comment;
import com.example.scheduledev.entity.Member;
import com.example.scheduledev.entity.Schedule;
import com.example.scheduledev.repository.CommentRepository;
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
public class
CommentService {
    private final CommentRepository commentRepository;
    //이부분에서 스케쥴,멤버 레퍼지토리부분을 순환참조가 되지않으면서분리해볼것. 모르면 물어봐야지.
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    //등록하기
    @Transactional
    public CommentResponseDto save(String contents, Long memberId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"일정 정보를 다시 조회해 주세요."));
        Member findmember = memberRepository.getReferenceById(memberId);
        Comment comment = new Comment(contents,findmember,schedule);
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(),
                comment.getSchedule().getId(),
                comment.getContent(),
                comment.getMember().getId(),
                comment.getMember().getUsername(),
                comment.getCreatedAt());
    }

    //전체 조회
    public List<CommentResponseDto> findAll() {
        return commentRepository.findAll().stream().map(CommentResponseDto::toDto).toList();
    }

    //id로 조회하기 단건
    public CommentResponseDto findById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"조회된 댓글이 없습니다."));
        return new CommentResponseDto(comment.getId(),
                comment.getSchedule().getId(),
                comment.getContent(),
                comment.getMember().getId(),
                comment.getMember().getUsername(),
                comment.getCreatedAt());
    }

    //댓글 수정 기능
    @Transactional
    public void updateComment(Long id, String content, Long memberId){
        Comment comment = validationId(id,memberId);
        comment.update(content);
    }


    //댓글 삭제 기능
    public void deleteComment(Long id, Long memberId){
        Comment comment = validationId(id,memberId);
        commentRepository.delete(comment);
    }

    /*id조회 메서드*/
    private Comment validationId(Long id, Long memberId ) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 수 있는 댓글이 없습니다."));
        if(!memberId.equals(comment.getMember().getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "댓글 작성자만 수정이 가능합니다.");
        }
        return comment;
    }

}
