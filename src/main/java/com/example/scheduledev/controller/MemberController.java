package com.example.scheduledev.controller;

import com.example.scheduledev.dto.*;
import com.example.scheduledev.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
    //로그인 구현 필요
    //애매하면 포스트
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(HttpServletRequest request,
                                                  @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = memberService.memberLogin(loginRequestDto.getEmail(),loginRequestDto.getPassword());

        HttpSession session = request.getSession(true);
        //member의 id
        session.setAttribute("token",loginResponseDto.getId());
        return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        LoginResponseDto response = memberService.memberLogin(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return ResponseEntity.ok(response);
    }



    //단건 조회 API
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
