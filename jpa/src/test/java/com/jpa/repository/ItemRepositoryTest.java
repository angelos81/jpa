package com.jpa.repository;

import com.jpa.constant.ItemStatus;
import com.jpa.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("Item 저장")
    public void saveItem_test() {
        // given
        Item item = new Item();
        item.setName("테스트 상품_01");
        item.setPrice(1000);
        item.setStock(1);
        item.setDesc("테스트 상품 입니다.");
        item.setStatus(ItemStatus.SELL);
        item.setRegDate(new Date());

        // when
        Item resultItem = itemRepository.save(item);

        // then
        assertNotNull(item.getId());
        System.out.println("item save result -> " + resultItem.toString());
    }
}