package com.refrigerator.recipe.dto;
import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.member.entity.Member;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.entity.RecipeCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class RecipeCreateDto {

    @NotBlank
    private String name;

    private Long userId;

    private Set<String> categoryNames;

    // 사용자 ID를 기반으로 Recipe 객체 생성
    public Recipe toRecipe(Long userId, Set<RecipeCategory> existingCategories) {
        Set<RecipeCategory> recipeCategories = new HashSet<>();

        for (String categoryName : categoryNames) {
            // 이미 존재하는 카테고리인지 확인
            RecipeCategory existingCategory = existingCategories.stream()
                    .filter(category -> category.getName().equals(categoryName))
                    .findFirst()
                    .orElseGet(() -> {
                        // 존재하지 않으면 새로 생성
                        RecipeCategory newCategory = new RecipeCategory();
                        newCategory.setName(categoryName);
                        return newCategory;
                    });
            recipeCategories.add(existingCategory);
        }

        return new Recipe(name, recipeCategories, userId);
    }
}
