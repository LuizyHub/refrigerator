// RecipeRequestDto.java
package com.refrigerator.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeRequestDto {
    private String name;
    private List<String> categories; // 카테고리 이름 리스트
}