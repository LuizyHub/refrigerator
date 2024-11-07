package com.refrigerator.itemCategory.service;
import com.refrigerator.itemCategory.entity.ItemCategory;
import com.refrigerator.itemCategory.repository.ItemCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public ItemCategory createItemCategory(String name) {
        ItemCategory category = new ItemCategory();
        category.setName(name);
        return itemCategoryRepository.save(category);
    }

    public ItemCategory getItemCategoryById(Long id) {
        return itemCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item Category not found"));
    }

    public List<ItemCategory> getAllItemCategories() {
        return itemCategoryRepository.findAll();
    }

    public ItemCategory updateItemCategory(Long id, String name) {
        ItemCategory category = getItemCategoryById(id);
        category.setName(name);
        return itemCategoryRepository.save(category);
    }

    public void deleteItemCategory(Long id) {
        itemCategoryRepository.deleteById(id);
    }
}