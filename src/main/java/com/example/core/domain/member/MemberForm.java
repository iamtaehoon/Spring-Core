package com.example.core.domain.member;

import com.example.core.domain.Grade;

public class MemberForm {
    private String userId;
    private String passwd;
    private Grade grade;

    public MemberForm(String userId, String passwd, Grade grade) {
        this.userId = userId;
        this.passwd = passwd;
        this.grade = grade;
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
}
