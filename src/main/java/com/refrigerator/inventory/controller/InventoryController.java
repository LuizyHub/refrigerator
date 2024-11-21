package com.refrigerator.inventory.controller;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.inventory.dto.InventoryCreateDto;
import com.refrigerator.inventory.dto.InventoryResponseDto;
import com.refrigerator.inventory.service.InventoryService;
import com.refrigerator.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/refrigerators")
@RequiredArgsConstructor
public class InventoryController {

  private final InventoryService inventoryService;

  // 냉장고의 모든 식재료 조회
  @GetMapping("/{refrigId}/inventories")
  public String getAllInventories(
      @CurrentMember Member member,
      @PathVariable Long refrigId,
      Model model
  ) {
    List<InventoryResponseDto> inventories = inventoryService.getAllInventories(member.getUserId(), refrigId);
    model.addAttribute("inventories", inventories);
    return "inventories"; // inventories.html 뷰로 이동
  }

  // 냉장고에 식재료 추가
  @PostMapping
  public ResponseEntity<String> addInventory(
      @RequestHeader("userId") Long userId,
      @Validated @RequestBody InventoryCreateDto createDto
  ) {
    inventoryService.addInventory(userId, createDto);
    return ResponseEntity.ok("Inventory created successfully.");
  }
}
