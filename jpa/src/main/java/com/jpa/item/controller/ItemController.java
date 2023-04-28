package com.jpa.item.controller;

import com.jpa.item.domain.dto.ItemDto;
import com.jpa.item.domain.dto.ItemSearchDto;
import com.jpa.item.domain.model.ItemListModel;
import com.jpa.item.domain.model.ItemModel;
import com.jpa.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    /**
     * 상품 정보 저장
     */
    @PostMapping("")
    public Object saveItem(@Valid @RequestBody ItemDto itemDto, BindingResult bindingResult) {
        log.debug("itemDto -> {}", itemDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Long itemId = itemService.saveItem(itemDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemId);
    }

    /**
     * 상품 정보 조회
     */
    @GetMapping(value = "/{itemId}")
    public Object getItemInfo(@PathVariable("itemId") Long itemId)  {
        log.debug("itemId -> {}", itemId);

        ItemModel model = itemService.getItemDetail(itemId);

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    /**
     * 상품 정보 수정
     */
    @PatchMapping(value = "/{itemId}")
    public Object updateItem(@Valid @RequestBody ItemDto itemDto, BindingResult bindingResult) {
        log.debug("itemDto -> {}", itemDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        itemService.updateItemInfo(itemDto);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 상품 목록 조회
     */
    @GetMapping("/list")
    public Object getItemList(@RequestBody ItemSearchDto itemSearchDto) {
        log.debug("itemSearchDto -> {}", itemSearchDto.toString());

        List modelList = itemService.getItemList(itemSearchDto);

        return ResponseEntity.status(HttpStatus.OK).body(modelList);
    }

    /**
     * 상품 목록 조회 (페이징)
     */
    @GetMapping("/listPage")
    public Object getItemListPage(@RequestBody ItemSearchDto itemSearchDto) {
        log.debug("itemSearchDto -> {}", itemSearchDto.toString());

        Pageable pageable = PageRequest.of(itemSearchDto.getPage(), itemSearchDto.getPageDiv());
        List<ItemListModel> modelList = itemService.getItemListPage(itemSearchDto, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(modelList);
    }
}
