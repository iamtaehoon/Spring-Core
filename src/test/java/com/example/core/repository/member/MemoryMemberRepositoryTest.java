package com.example.core.repository.member;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.example.core.domain.Grade;
import com.example.core.domain.member.Member;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    @AfterEach
    void afterEach() {
        memoryMemberRepository.clear();
    }

    @Test
    void 회원_저장() {
        //given, when
        Member saveMember = memoryMemberRepository.save("id0", "pw0", Grade.NORMAL, "010-1111-1111");

        //then
        assertThat(saveMember.getUserId()).isEqualTo("id0");
        assertThat(saveMember.getPasswd()).isEqualTo("pw0");
        assertThat(saveMember.getGrade()).isEqualTo(Grade.NORMAL);
    }

    @Test
    void 회원_id_조회_정상() {
        //given
        Member saveMember1 = memoryMemberRepository.save("id1", "pw1", Grade.NORMAL, "010-1111-1111");
        Member saveMember2 = memoryMemberRepository.save("id2", "pw2", Grade.NORMAL, "010-1111-1111");

        //when
        assertThat(memoryMemberRepository.findOne(saveMember2.getId()).get()).isEqualTo(saveMember2);
    }

    @Test
    void 존재않는_회원_id_조회_empty반환() {
        assertThat(memoryMemberRepository.findOne(0L)).isEqualTo(Optional.empty());
    }

    @Test
    void 회원_userId_조회_정상() {
        //given
        Member saveMember1 = memoryMemberRepository.save("id1", "pw1", Grade.NORMAL, "010-1111-1111");
        Member saveMember2 = memoryMemberRepository.save("id2", "pw2", Grade.NORMAL, "010-1111-1111");
        Member saveMember3 = memoryMemberRepository.save("id3", "pw3", Grade.VIP, "010-1111-1111");

        assertThat(memoryMemberRepository.findOneByUserId(saveMember2.getUserId()).get()).isEqualTo(saveMember2);
    }

    @Test
    void 존재않는_회원_userId_조회_empty반환() {
        assertThat(memoryMemberRepository.findOneByUserId("태훈")).isEqualTo(Optional.empty());
    }

    @Test
    void 회원_전체_조회() {
        //given
        Member saveMember1 = memoryMemberRepository.save("id1", "pw1", Grade.NORMAL, "010-1111-1111");
        Member saveMember2 = memoryMemberRepository.save("id2", "pw2", Grade.NORMAL, "010-1111-1111");
        Member saveMember3 = memoryMemberRepository.save("id3", "pw3", Grade.VIP, "010-1111-1111");
        Member notSaveMember = new Member(1000L, "id", "pw", Grade.NORMAL, "010-1111-1111");

        //when
        List<Member> allMembers = memoryMemberRepository.findAll();

        //then
        assertThat(allMembers.size()).isEqualTo(3);
        assertThat(allMembers.contains(saveMember1)).isTrue();
        assertThat(allMembers.contains(saveMember2)).isTrue();
        assertThat(allMembers.contains(saveMember3)).isTrue();
        assertThat(allMembers.contains(notSaveMember)).isFalse();
    }

    @Test
    void 회원_삭제() {
        //given
        Member saveMember1 = memoryMemberRepository.save("id1", "pw1", Grade.NORMAL, "010-1111-1111");
        Member saveMember2 = memoryMemberRepository.save("id2", "pw2", Grade.NORMAL, "010-1111-1111");
        Member saveMember3 = memoryMemberRepository.save("id3", "pw3", Grade.VIP, "010-1111-1111");

        //when
        memoryMemberRepository.deleteOne(saveMember1.getId());
        List<Member> allMembers = memoryMemberRepository.findAll();

        //then
        assertThat(allMembers.size()).isEqualTo(3-1);
        assertThat(allMembers.contains(saveMember1)).isFalse();
        assertThat(allMembers.contains(saveMember2)).isTrue();
    }
}