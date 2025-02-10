package com.example.scheduledev.controller;

import com.example.scheduledev.dto.CommentRequestDto;
import com.example.scheduledev.dto.CommentResponseDto;
import com.example.scheduledev.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(HttpServletRequest request, @RequestBody CommentRequestDto commentRequestDto) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");
        CommentResponseDto commentResponseDto = commentService.save(commentRequestDto.getContents(),memberId,commentRequestDto.getScheduleId());
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll() {
        List<CommentResponseDto> commentResponseDtoList = commentService.findAll();
        return new ResponseEntity<>(commentResponseDtoList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id){
        CommentResponseDto commentResponseDto = commentService.findById(id);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable Long id, HttpServletRequest request, @RequestBody CommentRequestDto commentRequestDto){
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");
        commentService.updateComment(id, commentRequestDto.getContents(), commentRequestDto.getScheduleId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable Long id, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");
        commentService.deleteComment(id, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
