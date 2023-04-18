package com.jpa.service;

import com.jpa.common.exception.ApiException;
import com.jpa.constant.MemberRole;
import com.jpa.member.entity.Member;
import com.jpa.member.model.MemberModel;
import com.jpa.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;


    @Test
    @DisplayName("회원가입 테스트 -> 정상")
    public void saveMember_test() {
        // given
        Member member = new Member();
        member.setId("h001");
        member.setName("홍길동");
        member.setEmail("hong001@test.com");
        member.setAddress("서울시 신림동");
        member.setRole(MemberRole.USER);

        // when
        MemberModel savedMember = memberService.saveMember(member);

        // then
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
    }

    @Test
    @DisplayName("회원가입 테스트 -> 중복 회원 에러")
    public void saveMemberDup_test() {
        // given
        Member member1 = new Member();
        member1.setId("h001");
        member1.setName("홍길동");
        member1.setEmail("hong001@test.com");
        member1.setAddress("서울시 신림동");
        member1.setRole(MemberRole.USER);
        memberService.saveMember(member1);

        Member member2 = new Member();
        member1.setId("a0001");
        member2.setName("가나다");
        member2.setEmail("hong001@test.com");
        member2.setAddress("서울시 대림동");
        member2.setRole(MemberRole.ADMIN);

        // when
        Throwable e = assertThrows(ApiException.class, () -> {memberService.saveMember(member2);});

        // then
        assertEquals("중복 가입된 회원", e.getMessage());
    }

}