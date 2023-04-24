package com.jpa.cart.repository;

import com.jpa.cart.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    /**
     * 회원에 등록된 카트 조회
     * @param memberId
     * @return Cart
     */
    Cart findByMemberId(String memberId);

}
