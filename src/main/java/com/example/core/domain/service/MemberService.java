package com.example.core.domain.service;

import com.example.core.domain.Grade;
import com.example.core.domain.member.MemberDTO;
import com.example.core.domain.member.MemberForm;

public interface MemberService {
    public MemberDTO join(MemberForm memberForm); // return 으로 memberDTO랑 Long 중 고민됨.

    public MemberDTO lookUp(String userId);

    //등급 갈아끼는 코드 필요함.
    public MemberDTO changeGrade(Long id, Grade grade);
}
