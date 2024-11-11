package com.refrigerator.recipeIngredient.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//레시피 재료 테이블
@Getter
@Setter
@Builder
public class RecipeIngredientDto {

    @Id
    private Long id;

    private Long recipeId;

    @NotBlank
    private Long stateId;

    private Integer unitId;

    private Double amount;
}
