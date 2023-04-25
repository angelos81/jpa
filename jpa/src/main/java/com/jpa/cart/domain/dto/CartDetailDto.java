package com.jpa.cart.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CartDetailDto {
    @NotBlank(message = "회원ID는 필수 입력 입니다.")
    private String memberId;
}
