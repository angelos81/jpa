package com.jpa.item.service;

import com.jpa.common.exception.ApiException;
import com.jpa.item.domain.dto.ItemDto;
import com.jpa.item.domain.dto.ItemImgDto;
import com.jpa.item.domain.dto.ItemSearchDto;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.domain.entity.ItemImg;
import com.jpa.item.domain.mapper.ItemMapper;
import com.jpa.item.domain.model.ItemImgModel;
import com.jpa.item.domain.model.ItemListModel;
import com.jpa.item.domain.model.ItemModel;
import com.jpa.item.repository.ItemImgRepository;
import com.jpa.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    /**
     * 상품 정보 저장
     * @param itemDto
     * @return Long
     */
    public Long saveItem(ItemDto itemDto) {
        // 상품 정보 생성
        Item item = new Item(itemDto.getName(), itemDto.getPrice(), itemDto.getStock(), itemDto.getDesc());

        // 상품 정보 저장
        itemRepository.save(item);
        log.info("Item.id -> {}", item.getId());

        // 상품 이미지 정보 저장
        boolean isFirstImg = true;
        List<ItemImgDto> itemImgList = itemDto.getItemImgList();
        for (ItemImgDto imgDto : itemImgList) {
            ItemImg itemImg = ItemImg.createItemImg(imgDto.getName(), imgDto.getUrl());
            itemImg.setItem(item);      // ItemImg -> Item 단방향 연관 관계

            if (isFirstImg) {
                itemImg.setRepImgYn("Y");
                isFirstImg = false;
            } else {
                itemImg.setRepImgYn("N");
            }

            itemImgService.saveItemImg(itemImg);
            log.debug("ItemImg.id -> {}", itemImg.getId());
        }

        return item.getId();
    }

    /**
     * 상품 정보 조회
     * @param itemId
     * @return ItemModel
     */
    @Transactional(readOnly = true)
    public ItemModel getItemDetail(Long itemId) {

        // Item.id 검색(Outer join) 후 ItemImg.id ASC
        // findByItemName(itemName) => Item.name으로 검색
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        List<ItemImgModel> itemImgModels = new ArrayList<>();
        for (ItemImg entity : itemImgList) {
            ItemImgModel imgModel = ItemMapper.INSTANCE.toItemImgModel(entity);
            itemImgModels.add(imgModel);
            log.info("ItemImgModel.toString -> {}", imgModel.toString());
        }

        Optional<Item> item = itemRepository.findById(itemId);
        ItemModel itemModel = ItemMapper.INSTANCE.toItemModel(item.get());
        itemModel.setItemImgList(itemImgModels);

        return itemModel;
    }

    /**
     * 상품 정보 수정
     * @param itemDto
     */
    public void updateItemInfo(ItemDto itemDto) {
        Long itemId = itemDto.getId();
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ApiException("상품 정보 없음"));

        /**
         * 영속 상태인 경우 변화가 감지되면 트랜잭션이 commit 될 때 update
         */
        // 상품 정보 업데이트
        item.updateItem(itemDto);
        //item.getDateInfo().setModDate(LocalDateTime.now());

        boolean isFirstImg = true;
        List<ItemImgDto> itemImgList = itemDto.getItemImgList();
        for (ItemImgDto imgDto : itemImgList) {
            itemImgService.updateItemImg(imgDto.getId(), imgDto);
        }
    }

    /**
     * 상품 목록 조회
     * @param itemSearchDto 
     * @return List
     */
    public List getItemList(ItemSearchDto itemSearchDto) {
        List<Item> itemList = itemRepository.search(itemSearchDto);
        log.info(itemList.toString());

        List itemModelList = ItemMapper.INSTANCE.toItemModelList(itemList);

        return itemModelList;
    }

    /**
     * 상품 목록 조회(페이징)
     * @param itemSearchDto
     * @param pageable
     * @return List<ItemListModel>
     */
    public List<ItemListModel> getItemListPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        Page<ItemListModel> page = itemRepository.searchPaging(itemSearchDto, pageable);

        List<ItemListModel> itemListModels = page.getContent();
        long totalCount = page.getTotalPages();

        log.info("itemListModels -> {}", itemListModels);
        log.info("totalCount -> {}", totalCount);

        // Repository에서 Entity를 Model로 변환하기 때문에 MapStruct 처리 필요 없음
//        List itemModelList = ItemMapper.INSTANCE.toItemModelList(itemList);

        return itemListModels;
    }
}
