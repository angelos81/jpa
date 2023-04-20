package com.jpa.item.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemListModel {
    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private String desc;
}
