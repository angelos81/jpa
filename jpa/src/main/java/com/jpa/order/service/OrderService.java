package com.jpa.order.service;

import com.jpa.common.exception.ApiException;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.domain.entity.ItemImg;
import com.jpa.item.repository.ItemImgRepository;
import com.jpa.item.repository.ItemRepository;
import com.jpa.member.domain.entity.Member;
import com.jpa.member.repository.MemberRepository;
import com.jpa.order.domain.dto.OrderDto;
import com.jpa.order.domain.entity.Order;
import com.jpa.order.domain.entity.OrderDetail;
import com.jpa.order.domain.model.OrderModel;
import com.jpa.order.repository.OrderDetailRepository;
import com.jpa.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ItemImgRepository itemImgRepository;

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

    /**
     * 주문 목록 조회
     * @param memberId
     * @param pageable
     * @return Page<OrderModel>
     */
    @Transactional(readOnly = true)
    public Page<OrderModel> gerOrderList(String memberId, Pageable pageable) {
        // 주문 카운트 조회 (JPQL 사용)
        Long totalCount = orderRepository.orderCount(memberId);

        // 주문 목록 조회 (JPQL 사용)
        List<Order> orderList = orderRepository.findOrderList(memberId, pageable);

        List<OrderModel> orderHistList = new ArrayList<>();
        for (Order order : orderList) {
            OrderModel orderHistDto = new OrderModel(order);

            // N+1 문제 발생 -> default_batch_fetch_size로 해결
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(orderDetail.getItem().getId(), "Y");
                if (itemImg != null) {
                    orderHistDto.setImgUrl(itemImg.getUrl());
                }
            }

            orderHistList.add(orderHistDto);
        }

        return new PageImpl<OrderModel>(orderHistList, pageable, totalCount);
    }
}
