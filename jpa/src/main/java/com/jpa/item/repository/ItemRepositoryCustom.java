package com.jpa.item.repository;

import com.jpa.item.domain.dto.ItemSearchDto;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.domain.model.ItemListModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {

    /**
     * 상품 목록 조회
     * @param itemSearchDto
     * @return List<Item>
     */
    List<Item> search(ItemSearchDto itemSearchDto);

    /**
     * 상품 목록 조회(페이징)
     * @param itemSearchDto
     * @param pageable
     * @return Page<ItemListModel>
     */
    Page<ItemListModel> searchPaging(ItemSearchDto itemSearchDto, Pageable pageable);
}
