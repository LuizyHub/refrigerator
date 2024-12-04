package com.refrigerator.recipe.controller;

import com.refrigerator.common.resolver.CurrentMember;
//import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.item.service.ItemCategoryService;
import com.refrigerator.item.service.ItemService;
import com.refrigerator.member.entity.Member;
import com.refrigerator.recipe.dto.RecipeCreateDto;
import com.refrigerator.recipe.dto.RecipeDto;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.entity.RecipeCategory;
import com.refrigerator.recipe.repository.RecipeCategoryRepository;
import com.refrigerator.recipe.repository.RecipeRepository;
import com.refrigerator.recipe.service.RecipeService;
import com.refrigerator.recipeingredient.dto.RecipeIngredientDto;
import com.refrigerator.recipeingredient.entity.RecipeIngredient;
import com.refrigerator.recipeingredient.service.RecipeIngredientService;
import com.refrigerator.refrig.entity.Refrigerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

// form => update, delete
@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeIngredientService recipeIngredientService;


    //새로운 레시피 생성 폼
    @GetMapping("/new")
    public String createRecipe(
            @ModelAttribute("recipe") RecipeCreateDto recipeCreateDto) {
        return "recipes/new";
    }

    // 새로운 레시피 생성 처리
    @PostMapping("/new")
    public String createRecipe(
            @CurrentMember Member member,
            @Valid @ModelAttribute("recipe") RecipeCreateDto recipeCreateDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "recipes/new";
        }

        recipeService.createRecipe(member, recipeCreateDto);
        return "redirect:/recipes";
    }


    //레시피 전체 조회 (카테고리별 조회)
    @GetMapping
    public String listRecipes(
            HttpServletRequest request,
            @CurrentMember Member member,
            @RequestParam(value = "categoryId", required = false) List<Long> categoryId,
            Model model) {

        List<Recipe> recipes;

        Long userId = member.getUserId();

        // 특정 카테고리의 레시피 조회
        if (categoryId != null && !categoryId.isEmpty()) {
            recipes = recipeService.getRecipesByCategoryIdsAndUserId(categoryId, userId);
            model.addAttribute("categoryIds", categoryId);
        } else {
            // 모든 레시피 조회
            recipes = recipeService.getAllRecipesByUserId(userId);
            model.addAttribute("categoryIds", List.of());
        }

        // 카테고리 및 레시피 데이터를 모델에 추가
        model.addAttribute("currentUri", request.getRequestURI());
        List<RecipeDto> list = recipes.stream().map(RecipeDto::fromEntity).toList();
        model.addAttribute("recipes", list);
        model.addAttribute("categories", recipeService.getAllCategories());

        return "recipes";
    }

    // 특정 레시피의 재료들 조회
    @GetMapping("/{recipeId}")
    public String getIngredients(@PathVariable Long recipeId, Model model) {
        List<RecipeIngredient> ingredients = recipeIngredientService.getIngredientsByRecipeId(recipeId);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("recipeId", recipeId);
        return "recipes/detail";
    }

    // 특정 레시피 삭제 처리
    @PostMapping("/{recipeId}/delete")
    public String deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipeById(recipeId);
        return "redirect:/recipes"; // 삭제 후 레시피 목록으로 리다이렉트
    }

    @GetMapping("/{recipeId}/refrigerators")
    public String getRefrigeratorByRecipeIdWhereItemIsEnough(
            @CurrentMember Member member,
            @PathVariable Long recipeId,
            Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(recipeId));
        Map<Refrigerator, Boolean> refrigeratorAvailability = recipeService.getRefrigeratorByRecipeIdWhereItemIsEnough(member.getUserId(), recipeId);
        model.addAttribute("refrigeratorAvailability", refrigeratorAvailability);
        return "recipes/check";
    }

}
