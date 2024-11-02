// IngredientRequestDto.java
package com.refrigerator.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientRequestDto {
    private Integer itemId;
    private Integer unitId;
    private Double amount;
}