package com.jpa.item.domain.dto;

import com.jpa.constant.ItemStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemSearchDto {
    private String name;

    private Integer price;

    private Integer stock;

    private String desc;

    private ItemStatus status;

    private int page;
    private int pageDiv;
}
