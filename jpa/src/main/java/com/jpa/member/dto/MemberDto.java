package com.jpa.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class MemberDto {
    @NotBlank(message = "ID는 필수 입력 입니다.")
    private String id;

    @NotBlank(message = "이름은 필수 입력 입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 입니다.")
    @Email(message = "이메일 형식을 확인해주세요.")
    private String email;

    @NotBlank(message = "이메일은 필수 입력 입니다.")
    @Length(min = 4, max = 16, message = "비밀번호는 4 ~ 16자 사이로 입력해주세요.")
    private String password;

    @NotBlank(message = "주소는 필수 입력 입니다.")
    private String address;
}
