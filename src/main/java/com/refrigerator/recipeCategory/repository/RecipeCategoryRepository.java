package com.refrigerator.recipeCategory.repository;

import com.refrigerator.recipeCategory.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {
}
