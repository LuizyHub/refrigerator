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
}
