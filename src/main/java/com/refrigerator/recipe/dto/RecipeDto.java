package com.refrigerator.recipe.dto;

import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.entity.RecipeCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDto {

    private Long recipeId;
    private String name;
    private Long userId;
    private Set<RecipeCategory> categories = new HashSet<>();

    public RecipeDto(Long recipeId, String name, Long userId, Set<RecipeCategory> categories) {
        this.recipeId = recipeId;
        this.name = name;
        this.userId = userId;
        this.categories = categories;
    }

    public String categoryNames() {
        StringBuilder sb = new StringBuilder();
        for (RecipeCategory category : categories) {
            sb.append(category.getName()).append(", ");
        }
        return sb.toString();
    }

    public static RecipeDto fromEntity(Recipe recipe) {
        return new RecipeDto(
                recipe.getRecipeId(),
                recipe.getName(),
                recipe.getUserId(),
                recipe.getCategories()
        );
    }
}
