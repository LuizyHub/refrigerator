package com.refrigerator.recipe.service;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.member.entity.Member;
import com.refrigerator.recipe.dto.RecipeCreateDto;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.entity.RecipeCategory;
import com.refrigerator.recipe.repository.RecipeCategoryRepository;
import com.refrigerator.recipe.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RecipeService {
  private final RecipeRepository recipeRepository;
  private final RecipeCategoryRepository recipeCategoryRepository;

  public RecipeService(RecipeRepository recipeRepository, RecipeCategoryRepository recipeCategoryRepository) {
    this.recipeRepository = recipeRepository;
    this.recipeCategoryRepository = recipeCategoryRepository;
  }

  // 새로운 Recipe 생성
  public void createRecipe(@CurrentMember Member member, RecipeCreateDto recipeCreateDto) {

    if (member == null) {
      throw new EntityNotFoundException("Member is null");
    }

    // 카테고리 조회 => 조회 안되면 새로 생성하는 로직 필요
    Set<RecipeCategory> categories = recipeCreateDto.getCategoryIds().stream()
        .map(categoryId -> recipeCategoryRepository.findById(categoryId.intValue())
            .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId)))
        .collect(Collectors.toSet());

    // Recipe 객체 생성
    Recipe recipe = recipeCreateDto.toRecipe(member, categories);
    // Recipe 저장
    recipeRepository.save(recipe);
  }

  //특정 레시피 조회
  public Recipe getRecipeById(Long id) {
    return recipeRepository.findById(id).
        orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
  }

  // 모든 레시피 조회
  public List<Recipe> getAllRecipes() {
    return recipeRepository.findAll();
  }
}

//public void createItem(ItemCreateDto itemCreateDto) {
//    ItemCategory category = itemCategoryRepository.findById(itemCreateDto.getCategoryId())
//            .orElseThrow(() -> new IllegalArgumentException("Category not found"));
//    State state = stateRepository.findById(itemCreateDto.getStateId())
//            .orElseThrow(() -> new IllegalArgumentException("State not found"));
//
//    Item item = itemCreateDto.toItem(category, state);
//    itemRepository.save(item);
//}
