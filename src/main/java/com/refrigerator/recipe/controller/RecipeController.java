package com.refrigerator.recipe.controller;

import com.refrigerator.common.resolver.CurrentMember;
//import com.refrigerator.item.entity.Item;
import com.refrigerator.member.entity.Member;
import com.refrigerator.recipe.dto.RecipeCreateDto;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.repository.RecipeCategoryRepository;
import com.refrigerator.recipe.repository.RecipeRepository;
import com.refrigerator.recipe.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// form => update, delete
@Controller
@RequestMapping("recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;
    private final RecipeCategoryRepository recipeCategoryRepository;

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

        // 카테고리 이름 분리 처리
        Set<String> parsedCategoryNames = Optional.ofNullable(recipeCreateDto.getCategoryNames())
                .orElse(Set.of())
                .stream()
                .flatMap(names -> Arrays.stream(names.split("[,\\s]+"))) // 쉼표 또는 공백으로 분리
                .filter(name -> !name.isBlank()) // 빈 값 제거
                .collect(Collectors.toSet());

        recipeCreateDto.setCategoryNames(parsedCategoryNames); // 분리된 카테고리 이름 설정
        recipeService.createRecipe(member, recipeCreateDto);
        return "redirect:/recipes";
    }


    //특정 레시피 조회
    @GetMapping ("/{recipeId}")
    public String getRecipeById(@PathVariable Long recipeId, Model model) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipes/detail";
    }

//    //모든 레시피 조회(카테고리 X 버전)
//    @GetMapping
//    public String getAllRecipes(Model model) {
//        List <Recipe> recipes = recipeService.getAllRecipes();
//        model.addAttribute("recipes", recipes);
//        return "recipes";
//    }

    @GetMapping
    public String listRecipes(
            @CurrentMember Member member,
            @RequestParam(value = "categoryId", required = false) List<Long> categoryId,
            Model model) {

        List<Recipe> recipes;

        Long userId = member.getUserId();

        // 특정 카테고리의 레시피 조회
        if (categoryId != null && !categoryId.isEmpty()) {
            recipes = recipeService.getRecipesByCategoryIdsAndUserId(categoryId, userId);
            model.addAttribute("selectedCategoryIds", categoryId);
        } else {
            // 모든 레시피 조회
            recipes = recipeService.getAllRecipesByUserId(userId);
            model.addAttribute("selectedCategoryIds", null);
        }

        // 카테고리 및 레시피 데이터를 모델에 추가
        model.addAttribute("recipes", recipes);
        model.addAttribute("categories", recipeService.getAllCategories());

        // 디버깅을 위한 로그 추가
        System.out.println("Recipes: " + recipes);
        System.out.println("Categories: " + model.getAttribute("categories"));
        return "recipes";
    }

    // 특정 레시피 삭제 처리
    @PostMapping("/{recipeId}/delete")
    public String deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipeById(recipeId);
        return "redirect:/recipes"; // 삭제 후 레시피 목록으로 리다이렉트
    }

//    @GetMapping ("/{recipeId}/edit")
//    public String editForm(@PathVariable Long recipeId, Model model) {
//        Recipe recipe = recipeService.getRecipeById(recipeId);
//        model.addAttribute("recipe", recipe);
//        return "recipes/editForm";
//    }
//
//
//    @PostMapping("/{recipeId}/edit")
//    public String edit(
//            @PathVariable Long recipeId,
//            @Valid @ModelAttribute("recipe") RecipeCreateDto recipeCreateDto,
//            BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "recipes/editForm";
//        }
//        //recipeParam 객체 생성
//        //recipeRepository.update(recipeId, recipeParam);
//        return "redirect:/recipeId";
//    }


}
