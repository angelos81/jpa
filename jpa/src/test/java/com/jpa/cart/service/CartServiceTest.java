package com.jpa.cart.service;

import com.jpa.cart.domain.dto.CartItemDto;
import com.jpa.constant.MemberRole;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.repository.ItemRepository;
import com.jpa.member.domain.entity.Member;
import com.jpa.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartServiceTest {
    @Autowired
    CartService cartService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;

    /**
     * 회원 정보 생성
     */
    public Member saveMember() {
        Member member = new Member();
        member.setId("test");
        member.setName("테스트");
        member.setEmail("test@test.com");
        member.setPassword("12345");
        member.setAddress("서울 신림동");
        member.setRole(MemberRole.USER);
        return memberRepository.save(member);
    }

    /**
     * 아이템 정보 생성
     */
    public Item saveItem() {
        Item item = new Item("아이템1", 1000, 100, "테스트 상품 입니다.");
        return itemRepository.save(item);
    }


    @Test
    @DisplayName("카드 저장")
    public void addCart_test() {
        // given
        Member member = saveMember();
        Item item = saveItem();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setMemberId(member.getId());
        cartItemDto.setItemId(item.getId());
        cartItemDto.setCount(10);

        // when
        Long cartId = cartService.addCart(cartItemDto);
        //CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cartId, )

        // then
        assertNotNull(cartId);
    }

}