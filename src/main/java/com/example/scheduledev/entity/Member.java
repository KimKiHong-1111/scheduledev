package com.example.scheduledev.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    //lv2.유저 엔티티 평가요소
    //일정 엔티티가 작성 유저명 대신 고유 식별자 필드를 포함하고있는가?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저명
    @Column(nullable = false, unique = true)
    private String username;

    //이메일
    @Column(nullable = false)
    private String email;

    //lv3 유저엔티티에 비밀번호 필드 추가.
    @Column(nullable = false)
    private String password;


    public Member() {

    }

    //id는 생성자에 넣을 필요가 없다.
    public Member(String username,String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
