package com.refrigerator.recipe.service;

import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.recipe.entity.RecipeCategory;
import com.refrigerator.recipe.repository.RecipeCategoryRepository;

import java.util.Optional;

public class RecipeCategoryService {

    private RecipeCategoryRepository recipeCategoryRepository;

    //새로운 RecipeCategory 생성 및 저장
    public RecipeCategory createRecipeCategory (String name) {
        // findByName 메서드 호출
        Optional<RecipeCategory> existingCategory = recipeCategoryRepository.findByName(name);
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category with name '" + name + "' already exists.");
        }

        // 생성자를 사용하여 RecipeCategory 객체 생성
        RecipeCategory category = new RecipeCategory(name);
        return recipeCategoryRepository.save(category);
    }

}
