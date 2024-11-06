package com.refrigerator.itemCategory.controller;

import com.refrigerator.itemCategory.dto.ItemCategoryCreateDto;
import com.refrigerator.itemCategory.dto.ItemCategoryResponseDto;
import com.refrigerator.itemCategory.service.ItemCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-categories")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping
    public ItemCategoryResponseDto createItemCategory(@RequestBody ItemCategoryCreateDto name) {
        return itemCategoryService.createItemCategory(name);
    }

    @GetMapping("/{id}")
    public ItemCategoryResponseDto getItemCategoryById(@PathVariable Long id) {
        return itemCategoryService.getItemCategoryById(id);
    }

    @GetMapping
    public List<ItemCategoryResponseDto> getAllItemCategories() {
        return itemCategoryService.getAllItemCategories();
    }

    @PutMapping("/{id}")
    public ItemCategoryResponseDto updateItemCategory(@PathVariable Long id, @RequestBody ItemCategoryCreateDto name) {
        return itemCategoryService.updateItemCategory(id, name);
    }

    @DeleteMapping("/{id}")
    public void deleteItemCategory(@PathVariable Long id) {
        itemCategoryService.deleteItemCategory(id);
    }
}