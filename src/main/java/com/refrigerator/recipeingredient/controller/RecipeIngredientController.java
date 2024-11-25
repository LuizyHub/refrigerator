package com.refrigerator.recipeingredient.controller;

import com.refrigerator.item.repository.ItemCategoryRepository;
import com.refrigerator.recipeingredient.dto.RecipeIngredientDto;
import com.refrigerator.recipeingredient.service.RecipeIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipes/{recipeId}/ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;
    private final ItemCategoryRepository itemCategoryRepository;


    // 1. 카테고리 선택 및 아이템 필터링
    @GetMapping("/items")
    public String selectItem(@PathVariable Long recipeId,
                             @RequestParam(required = false) Long categoryId,
                             Model model) {
        model.addAttribute("categories", recipeIngredientService.getAllCategories());
        if (categoryId != null) {
            model.addAttribute("items", recipeIngredientService.getItemsByCategory(categoryId));
            model.addAttribute("selectedCategoryId", categoryId);
        }
        return "recipeIngredient/selectCategory"; // 카테고리와 아이템 선택 화면
    }

    // 2. 선택된 아이템의 단위와 용량 입력
    @GetMapping("/items/{itemId}")
    public String addIngredientDetails(@PathVariable Long recipeId,
                                       @PathVariable Long itemId,
                                       Model model) {
        model.addAttribute("item", recipeIngredientService.getItemDetails(itemId));
        model.addAttribute("units", recipeIngredientService.getAllUnits());
        return "recipeIngredient/new"; // 단위와 용량 입력 화면
    }

    // 재료 저장
    @PostMapping("/save")
    public String saveIngredient(@PathVariable Long recipeId, @ModelAttribute RecipeIngredientDto ingredientDto) {
        ingredientDto.setRecipeId(recipeId); // recipeId 설정 (URL에서 받은 값으로 설정)
        recipeIngredientService.saveIngredient(ingredientDto); // 단일 재료 저장
        return "recipes/detail"; // 재료 목록 페이지로 리디렉션
    }

    // 특정 레시피의 제료 삭제 버튼
    @PostMapping("/{id}/delete")
    public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long id) {
        recipeIngredientService.deleteIngredient(id);
        return "redirect:/detail";
    }

    // 특정 레시피의 재료 추가 버튼
    @PostMapping("/add")
    public String addIngredient(@PathVariable Long recipeId, @ModelAttribute RecipeIngredientDto ingredientDto) {
        ingredientDto.setRecipeId(recipeId);
        recipeIngredientService.addIngredient(ingredientDto);
        return "redirect:recipes/" + recipeId + "/detail";
    }
}
