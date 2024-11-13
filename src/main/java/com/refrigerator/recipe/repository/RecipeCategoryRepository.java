package com.refrigerator.recipe.repository;

import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {

    // id로 RecipeCategory 조회
//    Optional<RecipeCategory> findById(Integer categoryId);
    Optional<RecipeCategory> findByName(String name);


}
