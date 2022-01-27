package com.example.core.service.member;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.core.domain.Grade;
import com.example.core.domain.member.Member;
import com.example.core.domain.member.MemberDTO;
import com.example.core.domain.member.MemberForm;
import com.example.core.repository.member.MemberRepository;
import com.example.core.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// Transaction 어노테이션은 JPA 적용했을 때.
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDTO join(MemberForm memberForm) {
        if (!memberRepository.findOneByUserId(memberForm.getUserId()).equals(Optional.empty())) {
            throw new IllegalArgumentException("이미 사용중인 id입니다.");
        }
        Member saveMember = memberRepository.save(memberForm.getUserId(), memberForm.getPasswd(),
            Grade.NORMAL, memberForm.getPhoneNum());//처음에는 당연히 normal이지.
        return new MemberDTO(saveMember);
    }

    @Override
    public MemberDTO lookUp(String userId) {
        if (memberRepository.findOneByUserId(userId).equals(Optional.empty())) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return new MemberDTO(memberRepository.findOneByUserId(userId).get());
    }

    @Override
    public MemberDTO changeGrade(Long id, Grade grade) {
        Member member = memberRepository.findOne(id).get();
        member.changeGrade(grade);
        return new MemberDTO(member);
    }
}
