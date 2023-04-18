package com.jpa.member.model;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;

@Getter
@Setter
@Builder
@ToString
public class MemberModel {
    private String id;

    private String name;

    private String email;


}
