package com.refrigerator.recipeingredient.service;

import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.item.repository.ItemCategoryRepository;
import com.refrigerator.item.repository.ItemRepository;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.repository.RecipeRepository;
import com.refrigerator.recipeingredient.dto.RecipeIngredientDto;
import com.refrigerator.recipeingredient.entity.RecipeIngredient;
import com.refrigerator.recipeingredient.repository.RecipeIngredientRepository;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final RecipeRepository recipeRepository;
    private final ItemRepository itemRepository;
    private final UnitRepository unitRepository;

    public List<RecipeIngredientDto> getIngredientsByRecipeId(Long recipeId) {
        return recipeIngredientRepository.findByRecipe_RecipeId(recipeId).stream().map(ingredient -> {
            RecipeIngredientDto dto = new RecipeIngredientDto();
            dto.setId(ingredient.getId());
            dto.setRecipeId(ingredient.getRecipe().getRecipeId());
            dto.setItemId(ingredient.getItem().getItemId());
            dto.setUnitId(ingredient.getUnit().getUnitId());
            dto.setAmount(ingredient.getAmount());
            return dto;
        }).collect(Collectors.toList());
    }

    public void addIngredient(RecipeIngredientDto dto) {
        RecipeIngredient ingredient = new RecipeIngredient();

        // Recipe 조회 및 예외 처리
        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid recipeId: " + dto.getRecipeId()));
        ingredient.setRecipe(recipe);

        // Item 조회 및 예외 처리
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid itemId: " + dto.getItemId()));
        ingredient.setItem(item);

        // Unit 조회 및 예외 처리
        Unit unit = unitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid unitId: " + dto.getUnitId()));
        ingredient.setUnit(unit);

        // 저장
        try {
            recipeIngredientRepository.save(ingredient);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save ingredient: " + e.getMessage(), e);
        }
    }


    public void deleteIngredient(Long id) {
        recipeIngredientRepository.deleteById(id);
    }

    public List<Item> getItemsByCategory(Long categoryId) {
        return itemRepository.findByCategory_CategoryId(categoryId);
    }

    public List<ItemCategory> getAllCategories() {
        return itemCategoryRepository.findAll();
    }

    // 특정 아이템의 세부 정보 조회
    public Item getItemDetails(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid itemId: " + itemId));
    }

    // 모든 단위 목록 조회
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    // 하나의 재료만 저장하는 메소드
    public void saveIngredient(RecipeIngredientDto dto) {
        // Recipe 조회 및 예외 처리
        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid recipeId: " + dto.getRecipeId()));

        // Item 조회 및 예외 처리
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid itemId: " + dto.getItemId()));

        // Unit 조회 및 예외 처리
        Unit unit = unitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid unitId: " + dto.getUnitId()));

        // RecipeIngredient 엔티티 생성 및 값 설정
        RecipeIngredient ingredient = new RecipeIngredient();
        ingredient.setRecipe(recipe);
        ingredient.setItem(item);
        ingredient.setUnit(unit);
        ingredient.setAmount(dto.getAmount());

        // 저장
        try {
            recipeIngredientRepository.save(ingredient);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save ingredient: " + e.getMessage(), e);
        }
    }
}
