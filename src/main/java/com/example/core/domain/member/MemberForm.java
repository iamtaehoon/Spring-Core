package com.example.core.domain.member;

public class MemberForm {
    private String userId;
    private String passwd;
    private String phoneNum;

    public MemberForm(String userId, String passwd, String phoneNum) {
        this.userId = userId;
        this.passwd = passwd;
        this.phoneNum = phoneNum;
    }

    public String getUserId() {
        return userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}