package com.refrigerator.recipeingredient.controller;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.item.service.ItemCategoryService;
import com.refrigerator.item.service.ItemService;
import com.refrigerator.member.entity.Member;
import com.refrigerator.recipeingredient.dto.RecipeIngredientDto;
import com.refrigerator.recipeingredient.service.RecipeIngredientService;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.service.UnitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipes/{recipeId}/ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;
    private final ItemCategoryService itemCategoryService;
    private final ItemService itemService;
    private final UnitService unitService;


    @GetMapping("/items")
    public String checkItems(
            HttpServletRequest request,
            @CurrentMember Member member,
            @PathVariable Long recipeId,
            @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
            Model model
    ) {
        List<ItemCategory> categories = itemCategoryService.getAllCategories();

        List<Item> items = categoryIds == null || categoryIds.isEmpty() ?
                itemService.getAllItems() :
                itemService.getItemsByCategoryId(categoryIds);

        model.addAttribute("categoryIds", categoryIds);
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("categories", categories);
        model.addAttribute("items", items);
        model.addAttribute("preUrl", "/recipes/" + recipeId);
        model.addAttribute("currentUri", request.getRequestURI());
        return "recipes/items";
    }



/*
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
*/

    @GetMapping("/new")
    public String addIngredient(
            HttpServletRequest request,
            @CurrentMember Member member,
            @PathVariable Long recipeId,
            @ModelAttribute("ingredient") RecipeIngredientDto ingredientDto,
            @RequestParam(value = "itemId", required = true) Long itemId,
            Model model
    ) {
        Item item = itemService.getItemById(itemId);
        List<Unit> units = unitService.getUnitsByState(item.getState());
        ingredientDto.setItemId(itemId);

        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("item", item);
        model.addAttribute("units", units);

        return "recipeIngredient/new"; // 단위와 용량 입력 화면
    }

    @PostMapping("/new")
    public String saveIngredient(
            HttpServletRequest request,
            @CurrentMember Member member,
            @PathVariable Long recipeId,
            @Valid @ModelAttribute("ingredient") RecipeIngredientDto ingredientDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Item item = itemService.getItemById(ingredientDto.getItemId());
            List<Unit> units = unitService.getUnitsByState(item.getState());
            model.addAttribute("currentUri", request.getRequestURI());
            model.addAttribute("recipeId", recipeId);
            model.addAttribute("item", item);
            model.addAttribute("units", units);
            return "recipeIngredient/new"; // 단위와 용량 입력 화면
        }

        recipeIngredientService.saveIngredient(ingredientDto);
        return "redirect:/recipes/" + recipeId; // 재료 목록 페이지로 리디렉션
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
    @PostMapping("/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        recipeIngredientService.deleteIngredient(ingredientId);
        return "redirect:/recipes/" + recipeId;
    }

    // 특정 레시피의 재료 추가 버튼
    @PostMapping("/add")
    public String addIngredient(@PathVariable Long recipeId, @ModelAttribute RecipeIngredientDto ingredientDto) {
        ingredientDto.setRecipeId(recipeId);
        recipeIngredientService.addIngredient(ingredientDto);
        return "redirect:recipes/" + recipeId + "/detail";
    }
}
