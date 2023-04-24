package com.jpa.order.domain.entity;

import com.jpa.common.domain.DateInfo;
import com.jpa.constant.OrderStatus;
import com.jpa.member.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    /**
     * 연관 관계의 주인은 외래키가 있는 곳으로 설정 <br>
     * 연관 관계의 주인이 외래키를 관리(등록, 수정, 삭제) <br>
     * 주인이 아닌 쪽은 mappedBy 속성값으로 연관 관계의 주인을 설정 <br>
     * 주인이 아닌 쪽은 읽기만 가능
     */
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Embedded
    private DateInfo dateInfo;


    /**
     * 주문정보 생성
     * @param member 
     * @return Order
     */
    public static Order createOrder(Member member) {
        Order order = new Order();
        order.setMember(member);

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        order.setDateInfo(new DateInfo(LocalDateTime.now(), null));

        return order;
    }

    /**
     * 주문 취소
     */
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        this.dateInfo.setModDate(LocalDateTime.now());

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.cancel();
        }
    }
}
