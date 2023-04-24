package com.jpa.order.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderCancelDto {
    @NotBlank(message = "회원ID는 필수 입력 입니다.")
    private String memberId;

    @NotNull(message = "주문ID는 필수 입력 입니다.")
    private Long orderId;
}
