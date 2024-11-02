package com.refrigerator.inventory.controller;

import com.refrigerator.inventory.dto.*;
import com.refrigerator.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refrigs/{refrigId}/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // 1. 재고 추가
    @PostMapping
    public ResponseEntity<InventoryResponseDto> addInventory(@PathVariable Long refrigId,
                                                             @RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto responseDto = inventoryService.addInventory(refrigId, inventoryRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 2. 재고 수정
    @PutMapping("/{inventoryId}")
    public ResponseEntity<Void> updateInventory(@PathVariable Long refrigId,
                                                @PathVariable Integer inventoryId,
                                                @RequestBody InventoryRequestDto inventoryRequestDto) {
        inventoryService.updateInventory(refrigId, inventoryId, inventoryRequestDto);
        return ResponseEntity.ok().build();
    }

    // 3. 재고 삭제
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long refrigId,
                                                @PathVariable Integer inventoryId) {
        inventoryService.deleteInventory(refrigId, inventoryId);
        return ResponseEntity.ok().build();
    }

    // 4. 재고 조회
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> getInventories(
            @PathVariable Long refrigId,
            @RequestParam(defaultValue = "false") boolean sortByEnd,
            @RequestParam(required = false) Integer categoryId) {
        List<InventoryResponseDto> inventories = inventoryService.getInventories(refrigId, sortByEnd, categoryId);
        return ResponseEntity.ok(inventories);
    }

    // 5. 재고 단일 조회
    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponseDto> getInventory(@PathVariable Long refrigId,
                                                             @PathVariable Integer inventoryId) {
        InventoryResponseDto inventory = inventoryService.getInventory(refrigId, inventoryId);
        return ResponseEntity.ok(inventory);
    }
}
