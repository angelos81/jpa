package com.jpa.cart.controller;

import com.jpa.cart.domain.dto.CartDetailDto;
import com.jpa.cart.domain.dto.CartItemDeleteDto;
import com.jpa.cart.domain.dto.CartItemDto;
import com.jpa.cart.domain.dto.CartOrderDto;
import com.jpa.cart.domain.model.CartDetailModel;
import com.jpa.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {
    private final CartService cartService;

    @PostMapping("")
    public Object addCart(@Valid @RequestBody CartItemDto cartItemDto, BindingResult bindingResult) {
        log.info("cartItemDto -> {}", cartItemDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Long cartId = null;

        try {
            cartId = cartService.addCart(cartItemDto);
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(cartId);
    }

    /**
     * 카트 상세 조회
     */
    @GetMapping("")
    public Object getCartInfo(@Valid @RequestBody CartDetailDto cartDetailDto, BindingResult bindingResult) {
        log.info("cartDetailDto -> {}", cartDetailDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<CartDetailModel> modelList = cartService.getCartDetailList(cartDetailDto.getMemberId());

        return ResponseEntity.status(HttpStatus.OK).body(modelList);
    }

    /**
     * 카트 아이템 수량 변경
     */
    @PatchMapping(value = "/item")
    public Object updateItemCount(@Valid @RequestBody CartItemDto cartItemDto, BindingResult bindingResult) {
        log.info("cartItemDto -> {}", cartItemDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        cartService.updateCartItem(cartItemDto);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 카트 아이템 삭제
     */
    @DeleteMapping("/item")
    public Object deleteItemCount(@Valid @RequestBody CartItemDeleteDto cartItemDeleteDto, BindingResult bindingResult) {
        log.info("cartItemDeleteDto -> {}", cartItemDeleteDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        cartService.deleteCartItem(cartItemDeleteDto);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 카트 이용 상품 주문
     */
    @PostMapping("/order")
    public Object orderCartItem(@Valid @RequestBody CartOrderDto cartOrderDto, BindingResult bindingResult) {
        log.info("cartOrderDto -> {}", cartOrderDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (cartOrderDto.getItemList().size() == 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Long orderId = cartService.cartOrder(cartOrderDto);

        return ResponseEntity.status(HttpStatus.OK).body(orderId);
    }
}
