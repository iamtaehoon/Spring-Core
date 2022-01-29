package com.example.core.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private String userId;
    private String passwd;
    private String phoneNum;

    public MemberForm(String userId, String passwd, String phoneNum) {
        this.userId = userId;
        this.passwd = passwd;
        this.phoneNum = phoneNum;
    }
}