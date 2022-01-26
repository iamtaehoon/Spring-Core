package com.example.core.domain.member;

import com.example.core.domain.Grade;

public class Member {
    private Long id;
    private String userId;
    private String passwd;
    private Grade grade;
    private String phoneNum;

    public Member(Long id, String userId, String passwd, Grade grade, String phoneNum) {
        this.id = id;
        this.userId = userId;
        this.passwd = passwd;
        this.grade = grade;
        this.phoneNum = phoneNum;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void changeGrade(Grade grade) {
        this.grade = grade;
    }
}
