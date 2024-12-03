package com.refrigerator.inventory.controller;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.inventory.dto.InventoryCreateDto;
import com.refrigerator.inventory.dto.InventoryResponseDto;
import com.refrigerator.inventory.service.InventoryService;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.item.service.ItemCategoryService;
import com.refrigerator.item.service.ItemService;
import com.refrigerator.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/refrigerators/{refrigId}/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final ItemCategoryService itemCategoryService;
    private final ItemService itemService;
    // 냉장고의 모든 식재료 조회
    @GetMapping
    public String getAllInventories(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            Model model
    ) {
        List<InventoryResponseDto> inventories = inventoryService.getAllInventories(member.getUserId(), refrigId);
        model.addAttribute("refrigId", refrigId);
        model.addAttribute("inventories", inventories);
        return "inventories"; // inventories.html 뷰로 이동
    }

    @GetMapping("/items")
    public String checkItems(
            HttpServletRequest request,
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
            Model model
    ) {
        List<ItemCategory> categories = itemCategoryService.getAllCategories();

        List<Item> items = categoryIds == null || categoryIds.isEmpty() ?
                itemService.getAllItems() :
                itemService.getItemsByCategoryId(categoryIds);

        model.addAttribute("categoryIds", categoryIds);
        model.addAttribute("refrigId", refrigId);
        model.addAttribute("categories", categories);
        model.addAttribute("items", items);
        model.addAttribute("preUrl", "/refrigerators/" + refrigId + "/inventories");
        model.addAttribute("currentUri", request.getRequestURI());


        return "items"; // items.html 뷰로 이동
    }

    // 냉장고에 식재료 추가
    @PostMapping
    public ResponseEntity<String> addInventory(
            @CurrentMember Member member,
            @Validated @RequestBody InventoryCreateDto createDto
    ) {
        inventoryService.addInventory(member.getUserId(), createDto);
        return ResponseEntity.ok("Inventory created successfully.");
    }
}
