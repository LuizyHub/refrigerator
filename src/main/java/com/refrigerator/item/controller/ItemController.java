package com.refrigerator.item.controller;

import com.refrigerator.item.dto.*;
import com.refrigerator.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 1. 재료 추가
    @PostMapping
    public ResponseEntity<ItemResponseDto> addItem(@RequestBody ItemRequestDto itemRequestDto) {
        ItemResponseDto responseDto = itemService.addItem(itemRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 2. 재료 조회
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getItems(
            @RequestParam(required = false) List<Integer> category_id,
            @RequestParam(required = false) Integer state_id) {
        List<ItemResponseDto> items = itemService.getItems(category_id, state_id);
        return ResponseEntity.ok(items);
    }

    // 3. 재료 단일 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> getItem(@PathVariable Integer itemId) {
        ItemResponseDto item = itemService.getItem(itemId);
        return ResponseEntity.ok(item);
    }

    // 4. 재료 수정
    @PutMapping("/{itemId}")
    public ResponseEntity<Void> updateItem(@PathVariable Integer itemId,
                                           @RequestBody ItemRequestDto itemRequestDto) {
        itemService.updateItem(itemId, itemRequestDto);
        return ResponseEntity.ok().build();
    }

    // 5. 재료 삭제
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }
}
