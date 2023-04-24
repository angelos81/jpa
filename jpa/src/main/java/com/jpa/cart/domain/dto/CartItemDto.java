package com.jpa.cart.domain.dto;

import com.jpa.cart.domain.entity.Cart;
import com.jpa.item.domain.entity.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CartItemDto {
    @NotBlank(message = "회원ID는 필수 입력 입니다.")
    private String memberId;

    @NotNull(message = "상품ID는 필수 입력 입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요.")
    private int count;
}
