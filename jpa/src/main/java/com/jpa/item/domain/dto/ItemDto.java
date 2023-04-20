package com.jpa.item.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class ItemDto {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 입니다.")
    private String name;
    
    @NotNull(message = "상품 가격은 필수 입력 입니다.")
    private Integer price;

    @NotNull(message = "상품 재고는 필수 입력 입니다.")
    private Integer stock;

    @NotBlank(message = "상품 설명은 필수 입력 입니다.")
    private String desc;

    private List<ItemImgDto> itemImgList;
}
