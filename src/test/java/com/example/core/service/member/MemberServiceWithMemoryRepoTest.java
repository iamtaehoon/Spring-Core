package com.example.core.service.member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.example.core.domain.Grade;
import com.example.core.domain.member.MemberDTO;
import com.example.core.domain.member.MemberForm;
import com.example.core.repository.member.MemoryMemberRepository;

class MemberServiceWithMemoryRepoTest {
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService = new MemberServiceImpl(memberRepository);

    @AfterEach
    void afterEach() {
        memberRepository.clear();
    }

    @Test
    void 회원_가입() {
        MemberForm memberForm = new MemberForm("id1", "pwd1", "010-1111-1111");
        Long joinMemberId = memberService.join(memberForm);
        MemberDTO joinMemberDTO = memberService.lookUp(joinMemberId);
        assertThat(joinMemberDTO.getUserId()).isEqualTo(memberForm.getUserId());
        assertThat(joinMemberDTO.getGrade()).isEqualTo(Grade.NORMAL);
    }

    @Test
    void 회원_가입_동일_userId_예외() {
        memberService.join(new MemberForm("id1", "pwd1", "010-1111-1111"));
        MemberForm memberForm = new MemberForm("id1", "pwd2", "010-1112-1111");
        assertThatThrownBy(() -> memberService.join(memberForm)).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("이미 사용중인 id입니다.");
    }

    @Test
    void 등급_변경() {
        memberService.join(new MemberForm("id1", "pwd1", "010-1111-1111"));
        Long id = memberRepository.findOneByUserId("id1").get().getId();

        memberService.modifyGrade(id);

        assertThat(memberService.lookUp(id).getGrade()).isEqualTo(Grade.VIP);
    }

    @Test
    void 회원_조회() {
        memberService.join(new MemberForm("id1", "pwd1", "010-1111-1111"));
        Long id = memberRepository.findOneByUserId("id1").get().getId();

        MemberDTO memberDTO = memberService.lookUp(id);
        assertThat(memberDTO.getUserId()).isEqualTo("id1");
        assertThat(memberDTO.getGrade()).isEqualTo(Grade.NORMAL);
    }

    @Test
    void 존재않는_회원_조회_예외() {
        Long id = 10000L;
        assertThatThrownBy(() -> memberService.lookUp(id)).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("존재하지 않는 회원입니다.");
    }
}