package com.refrigerator.itemCategory.dto;

import lombok.Getter;

@Getter
public class ItemCategoryResponseDto {

    // Getters
    private Long categoryId;
    private String name;

    // Constructor
    public ItemCategoryResponseDto(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

}
