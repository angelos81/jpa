package com.jpa.order;


import com.jpa.common.domain.DateInfo;
import com.jpa.common.exception.ApiException;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.repository.ItemRepository;
import com.jpa.member.domain.entity.Member;
import com.jpa.member.repository.MemberRepository;
import com.jpa.order.domain.entity.Order;
import com.jpa.order.domain.entity.OrderDetail;
import com.jpa.order.repository.OrderDetailRepository;
import com.jpa.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderTest {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @PersistenceContext
    EntityManager em;


    public Order createOrder() {
        Member member = new Member();
        member.setId("test001");
        member.setName("테스트");
        member.setEmail("abc@test.com");
        memberRepository.save(member);
        em.flush();

        Order order = new Order();
        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());
        order.setDateInfo(new DateInfo(LocalDateTime.now(), null));
        orderRepository.save(order);
        em.flush();

        for (int i=0; i<3; i++) {
            Item item = new Item("테스트 상품_"+i, 1000, 10, "상품 설명");
            itemRepository.save(item);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setItem(item);
            orderDetail.setCount(1);
            orderDetail.setPrice(1000);
            orderDetail.setOrder(order);
            orderDetailRepository.save(orderDetail);

            order.getOrderDetails().add(orderDetail);
        }


        return order;
    }



    @Test
    @DisplayName("지연로딩 테스트")
    public void lazyLoading_test() {
        Order order = createOrder();
        Long detailId = order.getOrderDetails().get(0).getId();
        em.flush();
        em.clear();

        OrderDetail orderDetail = orderDetailRepository.findById(detailId).orElseThrow(() -> new ApiException("데이터 없음"));
        System.out.println("OrderDetail class -> " + orderDetail.getOrder().getClass());

        System.out.println("Member.id -> " + orderDetail.getOrder().getMember().getId());
    }

}
