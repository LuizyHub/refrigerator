package com.refrigerator.recipe.repository;


import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByCategoriesInAndUserId(List<RecipeCategory> categories, Long userId);

    List<Recipe> findAllByUserId(Long userId);
}

