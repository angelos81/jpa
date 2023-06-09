package com.jpa.item.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemListModel {
    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private String desc;
}
