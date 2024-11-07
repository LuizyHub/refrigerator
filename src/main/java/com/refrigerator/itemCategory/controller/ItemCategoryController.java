package com.refrigerator.itemCategory.controller;

import com.refrigerator.itemCategory.entity.ItemCategory;
import com.refrigerator.itemCategory.service.ItemCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/item-categories")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping
    public ItemCategory createItemCategory(@RequestBody String name) {
        return itemCategoryService.createItemCategory(name);
    }

    @GetMapping("/{id}")
    public ItemCategory getItemCategoryById(@PathVariable Long id) {
        return itemCategoryService.getItemCategoryById(id);
    }

    @GetMapping
    public List<ItemCategory> getAllItemCategories() {
        return itemCategoryService.getAllItemCategories();
    }

    @PutMapping("/{id}")
    public ItemCategory updateItemCategory(@PathVariable Long id, @RequestBody String name) {
        return itemCategoryService.updateItemCategory(id, name);
    }

    @DeleteMapping("/{id}")
    public void deleteItemCategory(@PathVariable Long id) {
        itemCategoryService.deleteItemCategory(id);
    }
}