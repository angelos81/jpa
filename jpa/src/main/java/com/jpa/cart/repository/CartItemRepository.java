package com.jpa.cart.repository;

import com.jpa.cart.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /**
     * 카트에 저장되어 있는 아이템 조회
     * @param cartId
     * @param itemId
     * @return CartItem
     */
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
}
