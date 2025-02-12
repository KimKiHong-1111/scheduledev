package com.example.scheduledev.service;

import com.example.scheduledev.config.PasswordEncoder;
import com.example.scheduledev.dto.LoginResponseDto;
import com.example.scheduledev.dto.MemberResponseDto;
import com.example.scheduledev.dto.SignUpResponseDto;
import com.example.scheduledev.entity.Member;
import com.example.scheduledev.exception.ErrorCode;
import com.example.scheduledev.exception.PasswordException;
import com.example.scheduledev.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//기능구현은 서비스에다가 하기!
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    //회원가입 순서 유저명,이메일,비밀번호
    public SignUpResponseDto signUp(String username, String email, String password) {
        //암호화 처리,테스트 해보기.
        String encode = encoder.encode(password);
        Member member = new Member(username,email,encode);
        Member savedMember = memberRepository.save(member);
        return new SignUpResponseDto(savedMember.getId(),savedMember.getEmail(),savedMember.getPassword());
    }


    //전체찾기 회원 전체 조회
    public List<LoginResponseDto> findAll() {
        return memberRepository.findAll().stream().map(LoginResponseDto::toDto).toList();
    }


    //멤버 로그인
    public LoginResponseDto memberLogin(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 이메일 정보입니다."));

        validationPassword(password,member.getPassword());
        return new LoginResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }


    //아이디로 조회하기
    public MemberResponseDto findById(Long id) {

        Member member = memberRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"조회된 정보가 없습니다."));
        return new MemberResponseDto(member.getId(),member.getUsername(),member.getEmail());


    }
    //log.
    //비밀번호 변경 구현
    //묶어줘야하기 때문에 Transactional
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        Member findMember = memberRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"수정할 수 있는 데이터가 없습니다."));

        validationPassword(oldPassword,findMember.getPassword());

        findMember.updatePassword(newPassword);
    }

    //회원탈퇴
    public void deleteMember(Long id, String password) {
        Member member = memberRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"삭제할 수 있는 데이터가 없습니다."));

        validationPassword(password,member.getPassword());

        memberRepository.deleteById(member.getId());
    }

    /*비밀번호 검증 메서드*/
    public void validationPassword(String newPassword, String password) {
        boolean passwordMatch = encoder.matches(newPassword, password);
        if (!passwordMatch) {
            //예외처리
            throw new PasswordException(ErrorCode.UNAUTHORIZED);
        }

    }
}
