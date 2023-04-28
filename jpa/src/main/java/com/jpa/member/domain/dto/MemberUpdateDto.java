package com.jpa.member.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class MemberUpdateDto {
    private String id;

    @NotBlank(message = "이름은 필수 입력 입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 입니다.")
    @Email(message = "이메일 형식을 확인해주세요.")
    private String email;

    @NotBlank(message = "주소는 필수 입력 입니다.")
    private String address;
}
