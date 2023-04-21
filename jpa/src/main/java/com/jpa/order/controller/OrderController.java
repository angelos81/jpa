package com.jpa.order.controller;


import com.jpa.order.domain.dto.OrderDto;
import com.jpa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
