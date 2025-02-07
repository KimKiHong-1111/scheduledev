package com.example.scheduledev.controller;

import com.example.scheduledev.dto.MemberResponseDto;
import com.example.scheduledev.dto.SignUpRequestDto;
import com.example.scheduledev.dto.SignUpResponseDto;
import com.example.scheduledev.dto.UpdatePasswordRequestDto;
import com.example.scheduledev.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private MemberService memberService;

    //회원가입 API
    //이름,이메일,비밀번호
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto =
                memberService.signUp(
                        requestDto.getUsername(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    //아이디찾기?
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {

        MemberResponseDto memberResponseDto = memberService.findById(id);

        return new ResponseEntity<>(memberResponseDto,HttpStatus.OK);
    }

    //비밀번호 변경 API
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatedPassword(
            @PathVariable Long id,
            @RequestBody UpdatePasswordRequestDto requestDto
            ) {

        memberService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
