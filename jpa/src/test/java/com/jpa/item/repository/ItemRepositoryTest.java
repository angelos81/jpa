package com.jpa.item.repository;

import com.jpa.constant.ItemStatus;
import com.jpa.item.domain.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;


    /**
     * 샘플데이터 3개 생성
     * @param startIdx 
     */
    public void sampleData(int startIdx) {
        int randomInt = 0;

        for (int i = startIdx; i < (startIdx+3); i++) {
            randomInt = new Random().nextInt(100);

            Item item = new Item();
            item.setName("["+(i)+"] 상품");
            item.setPrice(100 * randomInt);
            item.setStock(randomInt);
            item.setDesc("상품 준비중 입니다.");
            item.setStatus(ItemStatus.SELL);
            //item.setRegDate(LocalDateTime.now());
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
        //item.setRegDate(LocalDateTime.now());

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
        sampleData(0);

        // when
        String itemName = "[1] 상품";
        String desc = "상품";
        List<Item> itemList = itemRepository.findByNameOrDesc(itemName, desc);

        //then
        assertEquals(1, itemList.size());
        for (Item item : itemList) {
            System.out.println("item.toString -> " + item.toString());
        }
    }

    @Test
    @DisplayName("Item 조회 -> 상품명, 설명 (item_id desc)")
    public void findByNameOrDescOrderByIdDesc_test() {
        // given
        sampleData(5);

        // when
        String itemName = "[5] 상품";
        String desc = "상품 준비중 입니다.";
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
        sampleData(5);

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
        sampleData(5);

        // when
        String desc = "준비";
        List<Item> itemList = itemRepository.findByItemDescNative(desc);

        //then
        //assertEquals(2, itemList.size());
        for (Item item : itemList) {
            System.out.println("item.toString -> " + item.toString());
        }
    }

}