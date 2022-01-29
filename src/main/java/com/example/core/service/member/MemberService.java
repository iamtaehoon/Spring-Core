package com.example.core.service.member;

import com.example.core.domain.Grade;
import com.example.core.domain.member.MemberDTO;
import com.example.core.domain.member.MemberForm;

public interface MemberService {
    public MemberDTO join(MemberForm memberForm); // return 으로 memberDTO랑 Long 중 고민됨.

    public MemberDTO lookUp(Long id);

    //등급 갈아끼는 코드 필요함.
    public MemberDTO modifyGrade(Long id);
}
