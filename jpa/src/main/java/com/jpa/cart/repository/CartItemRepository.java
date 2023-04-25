package com.jpa.cart.repository;

import com.jpa.cart.domain.entity.CartItem;
import com.jpa.cart.domain.model.CartDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /**
     * 카트에 저장되어 있는 아이템 조회
     * @param cartId
     * @param itemId
     * @return CartItem
     */
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    /**
     * 카트 상세 조회
     * @param cartId
     * @return List<CartDetailModel>
     */
//    @Query("select new com.jpa.cart.domain.model.CartDetailModel(ci.id, i.name, i.price, ci.count, im.url) " +
//            "from CartItem ci, ItemImg im " +
//            "join ci.item i " +
//            "where ci.cart.id = :cartId " +
//            "and ci.item.id = im.item.id " +
//            "and im.repImgYn = 'Y' ")
    @Query("select new com.jpa.cart.domain.model.CartDetailModel(ci.id, i.name, i.price, ci.count, im.url) " +
            "from CartItem ci, Item i, ItemImg im " +
            "where ci.cart.id = :cartId " +
            //"and ci.item.id = i.id " +
            "and ci.item.id = im.item.id " +
            "and im.item.id = i.id " +
            "and im.repImgYn = 'Y' ")
    List<CartDetailModel> findCartDetailList(Long cartId);
}
