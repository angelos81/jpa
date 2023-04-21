package com.jpa.order.service;

import com.jpa.common.exception.ApiException;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.repository.ItemRepository;
import com.jpa.member.domain.entity.Member;
import com.jpa.member.repository.MemberRepository;
import com.jpa.order.domain.dto.OrderDto;
import com.jpa.order.domain.entity.Order;
import com.jpa.order.domain.entity.OrderDetail;
import com.jpa.order.repository.OrderDetailRepository;
import com.jpa.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderDetailRepository orderDetailRepository;

    /**
     * 주문 정보 저장(바로주문)
     * @param orderDto
     * @return Long
     */
    public Long saveOrder(OrderDto orderDto) {
        // 회원정보 조회
        Member member = memberRepository.findById(orderDto.getMemberId()).orElseThrow(() -> new ApiException("회원 정보 없음"));

        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(() -> new ApiException("상품이 존재하지 않음"));

        Order order = Order.createOrder(member);
        orderRepository.save(order);

        OrderDetail orderDetail = OrderDetail.createOrderDetail(order, item, orderDto.getOrderCount());
        orderDetailRepository.save(orderDetail);

        return order.getId();
    }
}
