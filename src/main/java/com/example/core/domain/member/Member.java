package com.example.core.domain.member;

import com.example.core.domain.Grade;

public class Member {
    private Long id;
    private String userId;
    private String passwd;
    private Grade grade;

    public Member(Long id, String userId, String passwd, Grade grade) {
        this.id = id;
        this.userId = userId;
        this.passwd = passwd;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
}
