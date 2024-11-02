
// IngredientDto.java (RecipeResponseDto에서 사용)
package com.refrigerator.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDto {
    private Integer itemId;
    private Integer unitId;
    private Double amount;
}