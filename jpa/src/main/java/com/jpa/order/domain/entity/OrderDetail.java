package com.jpa.order.domain.entity;

import com.jpa.common.domain.DateInfo;
import com.jpa.item.domain.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_DETAIL")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {
    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer count;

    private Integer price;

    @Embedded
    private DateInfo dateInfo;
}
