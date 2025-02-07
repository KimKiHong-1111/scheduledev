package com.example.scheduledev.service;

import com.example.scheduledev.dto.MemberResponseDto;
import com.example.scheduledev.dto.SignUpResponseDto;
import com.example.scheduledev.entity.Member;
import com.example.scheduledev.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입 순서 유저명,이메일,비밀번호
    public SignUpResponseDto signUp(String username, String email, String password) {

        Member member = new Member(username,email,password);

        Member savedMember = memberRepository.save(member);

        return new SignUpResponseDto(savedMember.getId(),savedMember.getEmail(),savedMember.getPassword());
    }

    //아이디 찾기
    public MemberResponseDto findById(Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);

        //NPE방지
        if (optionalMember.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지않는 id = " + id);
        }
        Member findMember = optionalMember.get();

        //나이말고 다른 요소 조회되게끔 변경 필요
        return new MemberResponseDto(findMember.getUsername(),findMember.getEmail());

    }

    //비밀번호 변경 구현
    //묶어줘야하기 때문에 Transactional
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        if (!findMember.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }

        findMember.updatePassword(newPassword);
    }

}
