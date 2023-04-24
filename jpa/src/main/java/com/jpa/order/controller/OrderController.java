package com.jpa.order.controller;


import com.jpa.order.domain.dto.OrderCancelDto;
import com.jpa.order.domain.dto.OrderDto;
import com.jpa.order.domain.dto.OrderHistDto;
import com.jpa.order.domain.model.OrderHistModel;
import com.jpa.order.domain.model.OrderModel;
import com.jpa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    /**
     * 상품 주문(바로주문)
     */
    @PostMapping("")
    public Object saveOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        log.info("orderDto -> {}", orderDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Long orderId = orderService.saveOrder(orderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    /**
     * 주문 목록 조회
     */
    @GetMapping("/list")
    public Object orderHist(@RequestBody OrderHistDto orderHistDto) {
        log.info("orderHistDto -> {}", orderHistDto.toString());

        Pageable pageable = PageRequest.of(orderHistDto.getPage(), orderHistDto.getPageDiv());

        Page<OrderModel> dtoPage = orderService.gerOrderList(orderHistDto.getMemberId(), pageable);
        OrderHistModel model = OrderHistModel.builder()
                .totalCount(dtoPage.getTotalElements())
                .orderModels(dtoPage.getContent())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    /**
     * 주문 취소
     */
    @PutMapping("/cancel")
    public Object cancelOrder(@Valid @RequestBody OrderCancelDto orderCancelDto, BindingResult bindingResult) {
        log.info("orderCancelDto -> {}", orderCancelDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        orderService.cancelOrder(orderCancelDto.getMemberId(), orderCancelDto.getOrderId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT);
    }
}
