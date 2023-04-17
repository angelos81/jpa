package com.jpa.repository;

import com.jpa.constant.ItemStatus;
import com.jpa.entity.Item;
import com.jpa.entity.QItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;


    public void sampleData() {
        for (int i = 0; i< 10; i++) {
            Item item = new Item();
            item.setName("["+(i+1)+"] 상품");
            item.setPrice(1000 * i);
            item.setStock(1);
            item.setDesc((i+1) + " 테스트 상품 설명");
            item.setStatus(ItemStatus.SELL);
            item.setRegDate(new Date());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("Item 저장")
    public void saveItem_test() {
        // given
        Item item = new Item();
        item.setName("테스트 상품_99");
        item.setPrice(1000);
        item.setStock(99);
        item.setDesc("테스트 상품 입니다.");
        item.setStatus(ItemStatus.SELL);
        item.setRegDate(new Date());

        // when
        Item resultItem = itemRepository.save(item);

        // then
        assertNotNull(item.getId());
        System.out.println("item save result -> " + resultItem.toString());
    }

    @Test
    @DisplayName("Item 조회 -> 상품명, 설명")
    public void findByNameOrDesc_test() {
        // given
        sampleData();

        // when
        String itemName = "[1] 상품";
        String desc = "9 테스트 상품 설명";
        List<Item> itemList = itemRepository.findByNameOrDesc(itemName, desc);

        //then
        assertEquals(2, itemList.size());
        for (Item item : itemList) {
            System.out.println("item.toString -> " + item.toString());
        }
    }

    @Test
    @DisplayName("Item 조회 -> 상품명, 설명 (item_id desc)")
    public void findByNameOrDescOrderByIdDesc_test() {
        // given
        sampleData();

        // when
        String itemName = "[2] 상품";
        String desc = "9 테스트 상품 설명";
        List<Item> itemList = itemRepository.findByNameOrDescOrderByIdDesc(itemName, desc);

        //then
        //assertEquals(2, itemList.size());
        for (Item item : itemList) {
            System.out.println("item.toString -> " + item.toString());
        }
    }

    @Test
    @DisplayName("Item 조회 -> 상세설명 like 검색")
    public void findByItemDesc_test() {
        // given
        sampleData();

        // when
        String desc = "테스트";
        List<Item> itemList = itemRepository.findByItemDesc(desc);

        //then
        //assertEquals(2, itemList.size());
        for (Item item : itemList) {
            System.out.println("item.toString -> " + item.toString());
        }
    }

    @Test
    @DisplayName("Item 조회(Native) -> 상세설명 like 검색")
    public void findByItemDescNative_test() {
        // given
        sampleData();

        // when
        String desc = "테스트";
        List<Item> itemList = itemRepository.findByItemDescNative(desc);

        //then
        //assertEquals(2, itemList.size());
        for (Item item : itemList) {
            System.out.println("item.toString -> " + item.toString());
        }
    }

}