package com.jpa.member.domain.entity;

import com.jpa.common.domain.DateInfo;
import com.jpa.constant.MemberRole;
import com.jpa.member.domain.dto.MemberDto;
import com.jpa.order.domain.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

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
