package com.example.scheduledev.service;

import com.example.scheduledev.config.PasswordEncoder;
import com.example.scheduledev.dto.LoginResponseDto;
import com.example.scheduledev.dto.MemberResponseDto;
import com.example.scheduledev.dto.SignUpResponseDto;
import com.example.scheduledev.entity.Member;
import com.example.scheduledev.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//기능구현은 서비스에다가 하기!


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    //회원가입 순서 유저명,이메일,비밀번호
    public SignUpResponseDto signUp(String username, String email, String password) {

        Member member = new Member(username,email,password);

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

        if(!encoder.matches(password, member.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        };

        return new LoginResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }


    //아이디로 조회하기
    public MemberResponseDto findById(Long id) {

        Member member = memberRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"조회된 정보가 없습니다."));
        return new MemberResponseDto(member.getId(),member.getUsername(),member.getEmail());


    }

    //비밀번호 변경 구현
    //묶어줘야하기 때문에 Transactional
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        Member findMember = memberRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"수정할 수 있는 데이터가 없습니다."));

        if (!findMember.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }

        findMember.updatePassword(newPassword);
    }

    //회원탈퇴
    public void deleteMember(Long id, String password) {
        Member member = memberRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"삭제할 수 있는 데이터가 없습니다."));

        if (!encoder.matches(password, member.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }
        memberRepository.deleteById(member.getId());
    }

}
