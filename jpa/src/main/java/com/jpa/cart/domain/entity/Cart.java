package com.jpa.cart.domain.entity;

import com.jpa.common.domain.DateInfo;
import com.jpa.member.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CART")
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)  // @OneToOne, @ManyToOne의 기본 페치 전략은 EAGER (생략 가능)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private DateInfo dateInfo;


    /**
     * 카트 정보 생성
     * @param member
     * @return Cart
     */
    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        cart.setDateInfo(new DateInfo(LocalDateTime.now(), null));

        return cart;
    }
}
