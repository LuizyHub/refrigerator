package com.refrigerator.item.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateDto {

    private String name;
    private Long categoryId;
    private Long stateId;

    // Getters and Setters
}