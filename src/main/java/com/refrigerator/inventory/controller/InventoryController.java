package com.refrigerator.inventory.controller;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.inventory.dto.InventoryCreateDto;
import com.refrigerator.inventory.dto.InventoryResponseDto;
import com.refrigerator.inventory.entity.Inventory;
import com.refrigerator.inventory.service.InventoryService;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.item.service.ItemCategoryService;
import com.refrigerator.item.service.ItemService;
import com.refrigerator.member.entity.Member;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.service.UnitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/refrigerators/{refrigId}/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final ItemCategoryService itemCategoryService;
    private final ItemService itemService;
    private final UnitService unitService;

    // 냉장고의 모든 식재료 조회
    @GetMapping
    public String getAllInventories(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            Model model
    ) {
        List<InventoryResponseDto> inventories = inventoryService.getAllInventoriesWithPermission(member.getUserId(), refrigId);
        model.addAttribute("refrigId", refrigId);
        model.addAttribute("inventories", inventories);
        return "inventories"; // inventories.html 뷰로 이동
    }

    @GetMapping("/{inventoryId}")
    public String editInventoryForm(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            @PathVariable Integer inventoryId,
            Model model
    ) {
        Inventory inventory = inventoryService.getInventoryById(member.getUserId(), inventoryId);
//        InventoryDto inventoryDto = InventoryDto.fromEntity(inventory);
        Item item = inventory.getItem();
        List<Unit> units = unitService.getUnitsByState(item.getState());

        model.addAttribute("refrigId", refrigId);
        model.addAttribute("inventoryId", inventoryId);
        model.addAttribute("item", item);
        model.addAttribute("units", units);
        model.addAttribute("inventory", inventory);

        return "inventories/detail"; // inventories/detail.html 뷰로 이동
    }

    @PostMapping("/{inventoryId}/consume")
    public String consumeInventory(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            @PathVariable Integer inventoryId,
            @RequestParam(value = "amount", required = true) Double amount,
            @RequestParam(value = "unitId", required = true) Integer unitId
    ) {
        inventoryService.consumeInventoryWithPermission(member.getUserId(), inventoryId, amount, unitId);
        return "redirect:/refrigerators/" + refrigId + "/inventories";
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

    @GetMapping("/new")
    public String createInventoryForm(
            HttpServletRequest request,
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            @ModelAttribute("inventory") InventoryCreateDto createDto,
            @RequestParam(value = "itemId", required = true) Long itemId,
            Model model
    ) {
        Item item = itemService.getItemById(itemId);
        List<Unit> units = unitService.getUnitsByState(item.getState());
        createDto.setItemId(itemId);

        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("refrigId", refrigId);
        model.addAttribute("item", item);
        model.addAttribute("units", units);

        return "inventories/new"; // inventories/new.html 뷰로 이동
    }

    // 냉장고에 식재료 추가
    @PostMapping("/new")
    public String addInventory(
            HttpServletRequest request,
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            @Valid @ModelAttribute("inventory") InventoryCreateDto createDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Item item = itemService.getItemById(createDto.getItemId());
            List<Unit> units = unitService.getUnitsByState(item.getState());
            model.addAttribute("currentUri", request.getRequestURI());
            model.addAttribute("refrigId", refrigId);
            model.addAttribute("item", item);
            model.addAttribute("units", units);
            return "inventories/new";
        }
        inventoryService.addInventory(member.getUserId(), createDto);
        return "redirect:/refrigerators/" + createDto.getRefrigId() + "/inventories";
    }

    @PostMapping("/{inventoryId}/delete")
    public String deleteInventory(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            @PathVariable Integer inventoryId
    ) {
        inventoryService.deleteInventoryWithPermission(member.getUserId(), inventoryId);
        return "redirect:/refrigerators/" + refrigId + "/inventories";
    }
}
