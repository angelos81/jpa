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


    /**
     * 주문 상세 정보 생성
     * @param order 
     * @param item
     * @param count
     * @return OrderDetail
     */
    public static OrderDetail createOrderDetail(Order order, Item item, int count) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setItem(item);
        orderDetail.setCount(count);
        orderDetail.setPrice(item.getPrice() * count);

        // 상품 재고 변경
        item.orderStockRemove(count);

        return orderDetail;
    }

    /**
     * 상품 아이템 주문 취소 <br>
     *   - 상품 재고 증감
     */
    public void cancel() {
        this.getItem().orderStockAdd(this.count);
    }
}
