package com.refrigerator.itemCategory.service;

import com.refrigerator.itemCategory.dto.ItemCategoryCreateDto;
import com.refrigerator.itemCategory.dto.ItemCategoryResponseDto;
import com.refrigerator.itemCategory.entity.ItemCategory;
import com.refrigerator.itemCategory.repository.ItemCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public ItemCategoryResponseDto createItemCategory(ItemCategoryCreateDto itemCategoryCreateDto) {
        ItemCategory category = new ItemCategory();
        category.setName(itemCategoryCreateDto.getName());
        category = itemCategoryRepository.save(category);
        return new ItemCategoryResponseDto(category.getCategoryId(), category.getName());
    }

    public ItemCategoryResponseDto getItemCategoryById(Long id) {
        ItemCategory category = itemCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item Category not found"));
        return new ItemCategoryResponseDto(category.getCategoryId(), category.getName());
    }

    public List<ItemCategoryResponseDto> getAllItemCategories() {
        return itemCategoryRepository.findAll().stream()
                .map(category -> new ItemCategoryResponseDto(category.getCategoryId(), category.getName()))
                .collect(Collectors.toList());
    }

    public ItemCategoryResponseDto updateItemCategory(Long id, ItemCategoryCreateDto itemCategoryCreateDto) {
        ItemCategory category = itemCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item Category not found"));
        category.setName(itemCategoryCreateDto.getName());
        category = itemCategoryRepository.save(category);
        return new ItemCategoryResponseDto(category.getCategoryId(), category.getName());
    }

    public void deleteItemCategory(Long id) {
        itemCategoryRepository.deleteById(id);
    }
}
