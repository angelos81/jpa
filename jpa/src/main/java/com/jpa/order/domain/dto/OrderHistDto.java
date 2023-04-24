package com.jpa.order.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderHistDto {
    private String memberId;

    private int page;
    private int pageDiv;
}
