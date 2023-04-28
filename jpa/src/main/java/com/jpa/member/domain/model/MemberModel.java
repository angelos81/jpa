package com.jpa.member.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class MemberModel {
    private String id;

    private String name;

    private String email;

    private String address;

    public MemberModel() {}
    public MemberModel(String id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
