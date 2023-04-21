package com.jpa.order.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderDto {
    @NotBlank(message = "회원ID는 필수 입력 입니다.")
    private String memberId;

    @NotNull(message = "상품ID는 필수 입력 입니다.")
    private Long itemId;

    @NotNull(message = "상품ID는 필수 입력 입니다.")
    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    @Max(value = 10, message = "최대 주문 수량은 10개 입니다.")
    private Integer orderCount;
}
