package com.refrigerator.recipe.controller;

import com.refrigerator.recipe.dto.*;
import com.refrigerator.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    // 1. 레시피 추가
    @PostMapping
    public ResponseEntity<RecipeResponseDto> addRecipe(@RequestBody RecipeRequestDto recipeRequestDto) {
        RecipeResponseDto responseDto = recipeService.addRecipe(recipeRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 2. 레시피 수정
    @PutMapping("/{recipeId}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Integer recipeId,
                                             @RequestBody RecipeRequestDto recipeRequestDto) {
        recipeService.updateRecipe(recipeId, recipeRequestDto);
        return ResponseEntity.ok().build();
    }

    // 3. 레시피 삭제
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Integer recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.ok().build();
    }

    // 4. 레시피별 성분 추가
    @PostMapping("/{recipeId}/ingredients")
    public ResponseEntity<IngredientResponseDto> addIngredient(@PathVariable Integer recipeId,
                                                               @RequestBody IngredientRequestDto ingredientRequestDto) {
        IngredientResponseDto responseDto = recipeService.addIngredient(recipeId, ingredientRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 5. 레시피별 성분 수정
    @PutMapping("/{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<Void> updateIngredient(@PathVariable Integer recipeId,
                                                 @PathVariable Integer ingredientId,
                                                 @RequestBody IngredientRequestDto ingredientRequestDto) {
        recipeService.updateIngredient(recipeId, ingredientId, ingredientRequestDto);
        return ResponseEntity.ok().build();
    }

    // 6. 레시피별 성분 삭제
    @DeleteMapping("/{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Integer recipeId,
                                                 @PathVariable Integer ingredientId) {
        recipeService.deleteIngredient(recipeId, ingredientId);
        return ResponseEntity.ok().build();
    }

    // 7. 레시피 조회
    @GetMapping
    public ResponseEntity<List<RecipeResponseDto>> getRecipes(
            @RequestParam(required = false) String recipeCategoryName) {
        List<RecipeResponseDto> recipes = recipeService.getRecipes(recipeCategoryName);
        return ResponseEntity.ok(recipes);
    }

    // 8. 레시피 단일 조회
    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeResponseDto> getRecipe(@PathVariable Integer recipeId) {
        RecipeResponseDto recipe = recipeService.getRecipe(recipeId);
        return ResponseEntity.ok(recipe);
    }
}
