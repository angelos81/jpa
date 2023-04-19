package com.jpa.member.domain.entity;

import com.jpa.common.domain.DateInfo;
import com.jpa.constant.MemberRole;
import com.jpa.member.domain.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {
    @Id
    @Column(name = "member_id")
    private String id;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Embedded
    private DateInfo dateInfo;


    /**
     * 회원정보 엔티티 설정
     * @param memberDto
     * @return
     */
    public static Member setMember(MemberDto memberDto) {
        Member member = new Member();
        member.setId(memberDto.getId());
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setAddress(memberDto.getAddress());
        member.setRole(MemberRole.USER);
        member.setDateInfo(new DateInfo(LocalDateTime.now(), null));

        return member;
    }
}
