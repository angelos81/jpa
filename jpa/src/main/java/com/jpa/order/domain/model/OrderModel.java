package com.jpa.order.domain.model;

import com.jpa.constant.OrderStatus;
import com.jpa.order.domain.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class OrderModel {
    private Long orderId;

    private String orderDate;

    private OrderStatus orderStatus;

    private String imgUrl;

    public OrderModel(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }
}
