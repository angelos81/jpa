package com.jpa.member.repository;

import com.jpa.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    /**
     * email(=)을 통한 회원정보 조회
     * @param email 
     * @return Member
     */
    Member findByEmail(String email);
}
