package com.example.scheduledev.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    //일정 entity 평가요소 3가지.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //1.할일 제목
    @Column(nullable = false)
    private String title;

    //2.내용
    @Column(columnDefinition = "longtext")
    private String contents;

    //3.작성유저명
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    //Entity는 기본생성자가 필요
    public Schedule() {

    }

    //id는 자동생성되기 때문에 생성자 만들 필요 X
    public Schedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    //멤버 세터
    public void setMember(Member member) {
        this.member = member;
    }
}

