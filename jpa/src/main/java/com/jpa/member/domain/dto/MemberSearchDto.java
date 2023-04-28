package com.jpa.member.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberSearchDto {
    private String id;

    private String name;

    private String email;

    private String address;

    private int page;
    private int pageDiv;
}
