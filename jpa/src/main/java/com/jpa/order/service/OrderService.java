package com.jpa.order.service;

import com.jpa.common.exception.ApiException;
import com.jpa.common.exception.EntityNotFoundException;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.domain.entity.ItemImg;
import com.jpa.item.repository.ItemImgRepository;
import com.jpa.item.repository.ItemRepository;
import com.jpa.member.domain.entity.Member;
import com.jpa.member.repository.MemberRepository;
import com.jpa.order.domain.dto.OrderCartDto;
import com.jpa.order.domain.dto.OrderDto;
import com.jpa.order.domain.entity.Order;
import com.jpa.order.domain.entity.OrderDetail;
import com.jpa.order.domain.model.OrderModel;
import com.jpa.order.repository.OrderDetailRepository;
import com.jpa.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        Member member = memberRepository.findById(orderDto.getMemberId()).orElseThrow(() -> new EntityNotFoundException("회원 정보 없음"));

        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않음"));

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

            /*
             * N+1 문제 해결책
             *   1. fetch 전략을 지연 로딩으로 선택
             *   2. fetch size 조정 (default_batch_fetch_size 설정)
             */
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

    /**
     * 주문 취소
     * @param memberId
     * @param orderId
     */
    public void cancelOrder(String memberId, Long orderId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("회원 정보 없음"));

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("주문 정보 없음"));

        // 로그인 유저와 주문정보의 유저가 동일한지 체크
        if (!StringUtils.equals(member.getId(), order.getMember().getId())) {
            throw new ApiException("주문 취소 권한 없음");
        }

        // 영속성 컨텍스트로 인해 commit 시점에 update 쿼리 실행
        order.cancelOrder();
    }

    /**
     * 주문 정보 저장(카트)
     * @param orderCartDtoList
     * @param memberId
     * @return Long
     */
    public Long saveOrderOfCart(List<OrderCartDto> orderCartDtoList, String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("회원 정보 없음"));
        
        // 주문 정보 저장
        Order order = Order.createOrder(member);
        orderRepository.save(order);

        for (OrderCartDto dto : orderCartDtoList) {
            Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new EntityNotFoundException("상품 정보 없음"));

            OrderDetail orderDetail = OrderDetail.createOrderDetail(order, item, dto.getOrderCount());
            orderDetailRepository.save(orderDetail);
        }

        return order.getId();
    }
}
