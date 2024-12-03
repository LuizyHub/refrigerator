package com.refrigerator.recipeingredient.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RecipeIngredientDto {

    private Long id;
    
    private Long recipeId;
    
    private Long itemId;
    
    private Integer unitId;
    
    private Double amount;
}
