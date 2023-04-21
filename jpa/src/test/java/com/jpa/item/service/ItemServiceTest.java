package com.jpa.item.service;

import com.jpa.item.domain.dto.ItemDto;
import com.jpa.item.domain.dto.ItemImgDto;
import com.jpa.item.domain.dto.ItemSearchDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemImgService itemImgService;


    public ItemDto createItemDto() {
        ItemDto dto = new ItemDto();
        dto.setName("[1] 상품");
        dto.setPrice(1000);
        dto.setStock(10);
        dto.setDesc("[1] 상품 설명");

        List<ItemImgDto> itemImgList = new ArrayList<>();
        ItemImgDto imgDto1 = new ItemImgDto();
        imgDto1.setName("이미지_01");
        imgDto1.setUrl("http://test.co.kr/img/01.jpg");
        ItemImgDto imgDto2 = new ItemImgDto();
        imgDto2.setName("이미지_02");
        imgDto2.setUrl("http://test.co.kr/img/02.jpg");
        itemImgList.add(imgDto1);
        itemImgList.add(imgDto2);

        dto.setItemImgList(itemImgList);

        return dto;
    }

    @Test
    @DisplayName("상품 저장")
    public void saveItem_test() {
        // given
        ItemDto itemDto = createItemDto();

        // when
        Long itemId = itemService.saveItem(itemDto);

        // then
        assertNotNull(itemId);
        System.out.println("itemIc -> " + itemId);
    }

    @Test
    @DisplayName("상품 목록 (페이징 처리 없음)")
    public void getItemList_test() {
        // given
        ItemDto itemDto = createItemDto();
        Long itemId = itemService.saveItem(itemDto);
        ItemSearchDto itemSearchDto = ItemSearchDto.builder()
                .name("상품")
                .price(10)
                .build();

        // when
        List list = itemService.getItemList(itemSearchDto);

        // then
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("상품 목록 (페이징 처리)")
    public void getItemListPage_test() {
        // given
        ItemDto itemDto = createItemDto();
        Long itemId = itemService.saveItem(itemDto);
        ItemSearchDto itemSearchDto = ItemSearchDto.builder()
                .name("상품")
                .price(10)
                .build();

        Pageable pageable = PageRequest.of(0, 5);

        // when
        List list = itemService.getItemListPage(itemSearchDto, pageable);

        // then
        assertEquals(1, list.size());
    }
}