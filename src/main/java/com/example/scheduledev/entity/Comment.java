package com.example.scheduledev.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment() {
    }

    public Comment(String content, Member member, Schedule schedule) {
        this.content = content;
        this.member = member;
        this.schedule = schedule;
    }

    public void update(String content) {
        this.content = content;
    }
}
