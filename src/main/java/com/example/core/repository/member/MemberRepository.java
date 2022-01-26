package com.example.core.repository.member;

import java.util.List;
import java.util.Optional;

import com.example.core.domain.Grade;
import com.example.core.domain.member.Member;

public interface MemberRepository {
    public Member save(String userId, String passwd, Grade grade); //MemberForm에 의존하면 안됨.

    public Optional<Member> findOne(Long id);

    public Optional<Member> findOneByUserId(String userId);

    public List<Member> findAll();

    public Member deleteOne(Long id); //리턴 뭐로 해야할지 모르겠음. void or Member
}
