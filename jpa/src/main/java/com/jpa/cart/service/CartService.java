package com.jpa.cart.service;

import com.jpa.cart.domain.dto.CartItemDeleteDto;
import com.jpa.cart.domain.dto.CartItemDto;
import com.jpa.cart.domain.entity.Cart;
import com.jpa.cart.domain.entity.CartItem;
import com.jpa.cart.domain.model.CartDetailModel;
import com.jpa.cart.repository.CartItemRepository;
import com.jpa.cart.repository.CartRepository;
import com.jpa.common.exception.ApiException;
import com.jpa.common.exception.EntityNotFoundException;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.repository.ItemRepository;
import com.jpa.member.domain.entity.Member;
import com.jpa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;


    /**
     * 카트에 상품 추가
     * @param cartItemDto
     * @return Long
     */
    public Long addCart(CartItemDto cartItemDto) {
        // 회원 정보 조회
        Member member = memberRepository.findById(cartItemDto.getMemberId()).orElseThrow(() -> new EntityNotFoundException("회원 정보 없음"));

        // 아이템 정보 조회
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(() -> new EntityNotFoundException("상품 정보 없음"));

        // 기존 카트 정보 조회
        Cart cart = cartRepository.findByMemberId(cartItemDto.getMemberId());
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        log.info("cart.id -> {}", cart.getId());

        // 카트 아이템 정보 조회 (추가할려는 상품이 이미 카트에 저장되어 있는지 체크)
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), cartItemDto.getItemId());
        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
        } else {
            cartItem.addCount(cartItem.getCount());
        }
        log.info("cartItem.id -> {}", cartItem.getId());

        return cart.getId();
    }

    /**
     * 카트 상세 조회
     * @param memberId
     * @return List<CartDetailModel>
     */
    @Transactional(readOnly = true)
    public List<CartDetailModel> getCartDetailList(String memberId) {
        List<CartDetailModel> modelList = new ArrayList<>();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("회원 정보 없음"));

        Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart != null) {
            modelList = cartItemRepository.findCartDetailList(cart.getId());
        }

        return modelList;
    }

    /**
     * 카트 아이템 수량 변경
     * @param cartItemDto
     */
    public void updateCartItem(CartItemDto cartItemDto) {
        Cart cart = cartRepository.findByMemberId(cartItemDto.getMemberId());

        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), cartItemDto.getItemId());

        cartItem.updateCount(cartItemDto.getCount());
    }

    /**
     * 카트 아이템 삭제
     * @param cartItemDeleteDto
     */
    public void deleteCartItem(CartItemDeleteDto cartItemDeleteDto) {
        Member member = memberRepository.findById(cartItemDeleteDto.getMemberId()).orElseThrow(() -> new EntityNotFoundException("회원 정보 없음"));

        Cart cart = cartRepository.findByMemberId(cartItemDeleteDto.getMemberId());

        if (!StringUtils.equals(member.getId(), cart.getMember().getId())) {
            throw new ApiException("아이템 삭제 권한 없음");
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), cartItemDeleteDto.getItemId());

        cartItemRepository.delete(cartItem);
    }
}
