package com.refrigerator.recipe.repository;


import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByCategoriesInAndUserId(List<RecipeCategory> categories, Long userId);

    List<Recipe> findAllByUserId(Long userId);

    @Query("SELECT r FROM Recipe r " +
            "JOIN r.categories c " +
            "WHERE r.userId = :userId " +
            "AND c IN :categories " +
            "GROUP BY r.recipeId " +
            "HAVING COUNT(c.categoryId) = :categoriesSize")
    List<Recipe> findAllByExactCategoriesAndUserId(@Param("categories") List<RecipeCategory> categories,
                                                   @Param("categoriesSize") Integer categoriesSize,
                                                   @Param("userId") Long userId);
}

