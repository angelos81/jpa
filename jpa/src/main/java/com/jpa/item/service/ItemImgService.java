package com.jpa.item.service;

import com.jpa.common.exception.EntityNotFoundException;
import com.jpa.item.domain.dto.ItemImgDto;
import com.jpa.item.domain.entity.ItemImg;
import com.jpa.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemImgService {
    private final ItemImgRepository itemImgRepository;

    /**
     * 상품 이미지 저장
     * @param itemImg (Entity)
     * @return Long (ItemImg.id)
     */
    public Long saveItemImg(ItemImg itemImg) {
        /*
         * 이미지 저장은 따로 처리 하지 않음
         */

        // ITEM_IMG 정보 저장
        itemImgRepository.save(itemImg);

        return itemImg.getId();
    }

    public void updateItemImg(Long itemId, ItemImgDto itemImgDto) {
        /*
         * 영속 상태인 경우 변화가 감지되면 트랜잭션이 commit 될 때 update
         */

        //ItemImg itemImg = itemImgRepository.findById(itemId).orElseThrow(ApiException::new);
        ItemImg itemImg = itemImgRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("상품 이미지 정보 없음"));

        itemImg.updateItemImg(itemImgDto.getName(), itemImgDto.getUrl());
    }
}
