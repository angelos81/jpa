package com.jpa.cart.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class CartOrderDto {
    @NotBlank(message = "회원ID는 필수 입력 입니다.")
    private String memberId;

    @NotNull(message = "카트ID는 필수 입력 입니다.")
    private Long cartId;
    
    @NotNull(message = "카트내 상품ID는 필수 입력 입니다.")
    private List<Long> itemList;
}
