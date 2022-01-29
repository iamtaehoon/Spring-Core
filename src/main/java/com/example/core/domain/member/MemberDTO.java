package com.example.core.domain.member;

import com.example.core.domain.Grade;

import lombok.Getter;

@Getter
public class MemberDTO {
    private Long id;
    private String userId;
    private Grade grade;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.grade = member.getGrade();
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
            "userId='" + userId + '\'' +
            ", grade=" + grade +
            '}';
    }
}
