package com.jpa.entity;

import com.jpa.constant.ItemStatus;
import com.jpa.entity.embedded.DateInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
@Setter
@Getter
@ToString
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "item_name", nullable = false, length = 100)
    private String name;

    private Integer price;

    private Integer stock;

    private String desc;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @Embedded
    private DateInfo dateInfo;
}
