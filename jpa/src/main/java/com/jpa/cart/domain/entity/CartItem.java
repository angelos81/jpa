package com.jpa.cart.domain.entity;

import com.jpa.common.domain.DateInfo;
import com.jpa.item.domain.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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


    /**
     * 카트 아이템 정보 생성
     * @param cart 
     * @param item
     * @param count
     * @return CartItem
     */
    public static CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        cartItem.setDateInfo(new DateInfo(LocalDateTime.now(), null));

        return cartItem;
    }

    /**
     * 아이템 수량 증가
     * @param count
     */
    public void addCount(int count) {
        this.count += count;
    }

    /**
     * 아이템 수량 변경
     * @param count
     */
    public void updateCount(int count) {
        this.count = count;
    }
}
