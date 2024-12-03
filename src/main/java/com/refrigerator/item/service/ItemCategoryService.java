package com.refrigerator.item.service;

import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.item.repository.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    // 새로운 ItemCategory 생성 및 저장
    public ItemCategory createItemCategory(String name) {
        // findByName 메서드 호출
        Optional<ItemCategory> existingCategory = itemCategoryRepository.findByName(name);
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category with name '" + name + "' already exists.");
        }

        // 생성자를 사용하여 ItemCategory 객체 생성
        ItemCategory category = new ItemCategory(name);
        return itemCategoryRepository.save(category);
    }

    // name을 기준으로 ItemCategory 조회
    public Optional<ItemCategory> getItemCategoryByName(String name) {
        return itemCategoryRepository.findByName(name);
    }

    public List<ItemCategory> getAllCategories() {
        return itemCategoryRepository.findAll();
    }
}