package com.jpa.item.repository;

import com.jpa.item.domain.dto.ItemSearchDto;
import com.jpa.item.domain.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    /**
     * 상품 목록 조회
     * @param itemSearchDto
     * @return List<Item>
     */
    List<Item> search(ItemSearchDto itemSearchDto);
}
