package com.refrigerator.recipeingredient.repository;

import com.refrigerator.recipeingredient.entity.RecipeIngredient;
import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    List<RecipeIngredient> findByRecipe_RecipeId(Long recipeId);

}
