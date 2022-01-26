package com.example.core.domain.member;

import com.example.core.domain.Grade;

public class MemberDTO {
    private String userId;
    private Grade grade;

    public MemberDTO(Member member) {
        this.userId = member.getUserId();
        this.grade = member.getGrade();
    }

    public String getUserId() {
        return userId;
    }

    public Grade getGrade() {
        return grade;
    }
}
