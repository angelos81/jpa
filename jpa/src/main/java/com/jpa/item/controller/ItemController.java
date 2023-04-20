package com.jpa.item.controller;

import com.jpa.item.domain.dto.ItemDto;
import com.jpa.item.domain.dto.ItemSearchDto;
import com.jpa.item.domain.model.ItemModel;
import com.jpa.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("")
    public Object saveItem(@Valid @RequestBody ItemDto itemDto, BindingResult bindingResult) {
        log.info("itemDto -> {}", itemDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Long itemId = itemService.saveItem(itemDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemId);
    }

    @GetMapping(value = "/{itemId}")
    public Object getItemInfo(@PathVariable("itemId") Long itemId)  {
        log.info("itemId -> {}", itemId);

        ItemModel model = itemService.getItemDetail(itemId);

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @PostMapping(value = "/{itemId}")
    public Object updateItem(@Valid @RequestBody ItemDto itemDto, BindingResult bindingResult) {
        log.info("itemDto -> {}", itemDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        itemService.updateItemInfo(itemDto);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/list")
    public Object getItemList(@RequestBody ItemSearchDto itemSearchDto) {
        log.info("itemSearchDto -> {}", itemSearchDto.toString());

        List modelList = itemService.getItemList(itemSearchDto);

        return ResponseEntity.status(HttpStatus.OK).body(modelList);
    }
}
