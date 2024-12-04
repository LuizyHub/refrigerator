package com.refrigerator.recipe.service;

import com.refrigerator.common.helper.RecipeHelper;
import com.refrigerator.item.repository.ItemRepository;
import com.refrigerator.member.entity.Member;
import com.refrigerator.permission.service.MemberRefrigService;
import com.refrigerator.recipe.dto.RecipeCreateDto;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.entity.RecipeCategory;
import com.refrigerator.recipe.repository.RecipeCategoryRepository;
import com.refrigerator.recipe.repository.RecipeRepository;
import com.refrigerator.recipeingredient.entity.RecipeIngredient;
import com.refrigerator.recipeingredient.service.RecipeIngredientService;
import com.refrigerator.refrig.entity.Refrigerator;
import com.refrigerator.unit.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCategoryRepository recipeCategoryRepository;
    private final MemberRefrigService memberRefrigService;
    private final RecipeIngredientService recipeIngredientService;
    private final RecipeHelper recipeHelper;

    // 새로운 Recipe 생성
    public void createRecipe(Member member, RecipeCreateDto recipeCreateDto) {

        if (member == null) {
            throw new EntityNotFoundException("Member is null");
        }

        Set<RecipeCategory> categories = Optional.ofNullable(recipeCreateDto.getCategories())
                .orElse(Set.of()) // categoryNames가 null이면 빈 Set 반환
                .stream() // Set<String>을 Stream<String>으로 변환
                .map(name -> recipeCategoryRepository.findByName(name.trim()) // 이름으로 카테고리 조회
                        .orElseGet(() -> recipeCategoryRepository.save(new RecipeCategory(name.trim())))) // 없으면 생성
                .collect(Collectors.toSet()); // 최종적으로 Set<RecipeCategory>로 변환


        // Recipe 객체 생성
        Recipe recipe = recipeCreateDto.toRecipe(member.getUserId(), categories);
        // Recipe 저장
        recipeRepository.save(recipe);
    }

    //특정 레시피 조회
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
    }

    public List<Recipe> getRecipesByCategoryIdsAndUserId(List<Long> categoryIds, Long userId) {
        List<RecipeCategory> categories = recipeCategoryRepository.findAllByCategoryIdIn(categoryIds);
        return recipeRepository.findAllByExactCategoriesAndUserId(categories, categories.size(), userId);
    }

    public List<Recipe> getAllRecipesByUserId(Long userId) {
        return recipeRepository.findAllByUserId(userId);
    }

    // 모든 카테고리 조회
    public List<RecipeCategory> getAllCategories() {
        return recipeCategoryRepository.findAll();
    }

    // 특정 Recipe 삭제
    public void deleteRecipeById(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new IllegalArgumentException("Recipe not found with id: " + recipeId);
        }
        recipeRepository.deleteById(recipeId);
    }

    public Map<Refrigerator, Boolean> getRefrigeratorByRecipeIdWhereItemIsEnough(Long userId, Long recipeId) {
        List<Refrigerator> refrigerators = memberRefrigService.getAllReadableRefrigerators(userId);
        List<RecipeIngredient> ingredients = recipeIngredientService.getIngredientsByRecipeId(recipeId);

        return refrigerators.stream()
                .collect(Collectors.toMap(
                        refrigerator -> refrigerator,
                        refrigerator -> recipeHelper.hasEnoughIngredients(refrigerator.getRefrigId(), ingredients)
                ));
    }

}