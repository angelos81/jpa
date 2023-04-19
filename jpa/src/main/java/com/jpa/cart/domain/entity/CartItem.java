package com.jpa.cart.domain.entity;

import com.jpa.common.domain.DateInfo;
import com.jpa.item.domain.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CART_ITEM")
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    @Embedded
    private DateInfo dateInfo;
}
