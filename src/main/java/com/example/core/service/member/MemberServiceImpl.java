package com.example.core.service.member;

import java.util.ArrayList;
import java.util.List;
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
    public Long join(MemberForm memberForm) {
        if (!memberRepository.findOneByUserId(memberForm.getUserId()).equals(Optional.empty())) {
            throw new IllegalArgumentException("이미 사용중인 id입니다.");
        }
        Member saveMember = memberRepository.save(memberForm.getUserId(), memberForm.getPasswd(),
            Grade.NORMAL, memberForm.getPhoneNum());//처음에는 당연히 normal이지.
        return saveMember.getId();
    }

    @Override
    public MemberDTO lookUp(Long id) {
        if (memberRepository.findOne(id).equals(Optional.empty())) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return new MemberDTO(memberRepository.findOne(id).get());
    }

    @Override
    public MemberDTO modifyGrade(Long id) {
        Member member = memberRepository.findOne(id).get();
        member = switchGrade(member);
        return new MemberDTO(member);
    }

    @Override
    public List<MemberDTO> findAll() {
        List<Member> allMembers = memberRepository.findAll();
        List<MemberDTO> allMembersDTO = new ArrayList<>();
        allMembers.stream().forEach(member -> allMembersDTO.add(new MemberDTO(member)));
        return allMembersDTO;
    }

    @Override
    public Long findOneUsingName(String memberName) {
        Optional<Member> wrappingMember = memberRepository.findOneByUserId(memberName);
        if (wrappingMember.isEmpty()) {
            throw new IllegalArgumentException("해당 멤버는 존재하지 않습니다.");
        }
        return wrappingMember.get().getId();
    }

    private Member switchGrade(Member member) {
        Grade grade = member.getGrade();
        if (grade == Grade.VIP) {
            grade = Grade.NORMAL;
            return member.changeGrade(grade);
        }
        if (grade == Grade.NORMAL) {
            grade = Grade.VIP;
            return member.changeGrade(grade);
        }
        throw new IllegalArgumentException("로직에러.");
    }
}
