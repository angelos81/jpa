package com.jpa.cart;


import com.jpa.cart.domain.entity.Cart;
import com.jpa.cart.repository.CartRepository;
import com.jpa.common.domain.DateInfo;
import com.jpa.common.exception.EntityNotFoundException;
import com.jpa.constant.MemberRole;
import com.jpa.member.domain.entity.Member;
import com.jpa.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
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
public class CartTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartRepository cartRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("카트 저장")
    public void cartSave_test() {
        // given
        Member member = new Member();
        member.setId("h001");
        member.setName("홍길동");
        member.setEmail("hong001@test.com");
        member.setAddress("서울시 신림동");
        member.setRole(MemberRole.USER);
        memberRepository.save(member);
        em.flush();

        Cart cart = new Cart();
        cart.setMember(member);
        cart.setDateInfo(new DateInfo(LocalDateTime.now(), null));
        cartRepository.save(cart);

        // when
        em.flush();
        em.clear();

        Cart searchCart = cartRepository.findById(cart.getId()).orElseThrow(() -> new EntityNotFoundException("조회 데이터 없음"));

        // then
        Assertions.assertEquals(cart.getId(), searchCart.getId());
    }
}
