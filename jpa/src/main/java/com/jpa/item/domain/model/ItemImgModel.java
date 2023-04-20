package com.jpa.item.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemImgModel {
    private Long id;

    private String name;

    private String url;

    private String repImgYn;
}
