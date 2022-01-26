package com.example.core.repository.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.core.domain.Grade;
import com.example.core.domain.member.Member;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    private static final Map<Long, Member> repository = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(String userId, String passwd, Grade grade, String phoneNum) {
        Member member = new Member(sequence++, userId, passwd, grade, phoneNum);
        repository.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findOne(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Optional<Member> findOneByUserId(String userId) {
        return repository.values().stream()
            .filter(member -> member.getUserId().equals(userId)).findAny();
    }
    @Override
    public List<Member> findAll() { // 구현체를 보내도 되는걸까? 헷갈리네 그렇다고 하면 여기 함수 반환타입도 바꿔야 하나?
        return new ArrayList<>(repository.values());
    }

    @Override
    public Member deleteOne(Long id) {
        return repository.remove(id);
    }

    protected void clear() {
        repository.clear();
    }
}