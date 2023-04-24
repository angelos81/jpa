package com.jpa.order.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHistModel {
    private Long totalCount;

    private List<OrderModel> orderModels;
}
