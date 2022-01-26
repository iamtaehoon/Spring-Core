package com.example.core.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.core.domain.Grade;
import com.example.core.domain.member.MemberDTO;
import com.example.core.domain.member.MemberForm;
import com.example.core.repository.member.MemoryMemberRepository;

class MemberServiceImplTest { //클래스 이름 바꿔야겠다. MemberServiceImpl이 아니라 memoryMemberRepo 썻다고 가르쳐줘야할듯?
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService = new MemberServiceImpl(memberRepository);

    @AfterEach
    void afterEach() {
        memberRepository.clear();
    }

    @Test
    void 회원_가입() {
        MemberForm memberForm = new MemberForm("id1", "pwd1", "010-1111-1111");
        MemberDTO joinMember = memberService.join(memberForm);
        assertThat(joinMember.getUserId()).isEqualTo(memberForm.getUserId());
        assertThat(joinMember.getGrade()).isEqualTo(Grade.NORMAL);
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

        memberService.changeGrade(id, Grade.VIP);

        assertThat(memberService.lookUp("id1").getGrade()).isEqualTo(Grade.VIP);
    }

    @Test
    void 회원_조회() {
        memberService.join(new MemberForm("id1", "pwd1", "010-1111-1111"));
        MemberDTO memberDTO = memberService.lookUp("id1");
        assertThat(memberDTO.getUserId()).isEqualTo("id1");
        assertThat(memberDTO.getGrade()).isEqualTo(Grade.NORMAL);
    }

    @Test
    void 존재않는_회원_조회_예외() {
        assertThatThrownBy(() -> memberService.lookUp("id1")).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("존재하지 않는 회원입니다.");
    }
}