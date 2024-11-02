// RecipeResponseDto.java
package com.refrigerator.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeResponseDto {
    private Integer recipeId;
    private String name;
    private List<IngredientDto> ingredients;
    private List<String> categories;
}