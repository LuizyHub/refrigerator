package com.refrigerator.itemCategory.dto;

import lombok.Getter;

@Getter
public class ItemCategoryResponseDto {

    // Getters
    private final Long categoryId;
    private final String name;

    // Constructor
    public ItemCategoryResponseDto(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
}