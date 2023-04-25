package com.jpa.cart.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailModel {
    private Long cartItemId;

    private String itemName;

    private int price;

    private int count;

    private String itemUrl;

    public CartDetailModel(Long cartItemId, String itemName, int price, int count, String itemUrl) {
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.itemUrl = itemUrl;
    }
}
