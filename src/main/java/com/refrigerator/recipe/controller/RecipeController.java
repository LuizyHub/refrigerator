package com.refrigerator.recipe.controller;

import com.refrigerator.common.resolver.CurrentMember;
//import com.refrigerator.item.entity.Item;
import com.refrigerator.member.dto.MemberLoginDto;
import com.refrigerator.member.entity.Member;
import com.refrigerator.recipe.dto.RecipeCreateDto;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//만들 수 있는 레시피 조회


@Controller
@RequestMapping("/recipes")

public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    //새로운 레시피 생성 폼
    @PostMapping("/new")
    public String createRecipe(
            @ModelAttribute("recipe") RecipeCreateDto recipeCreateDto) {
        return "recipes/new";
    }

    //새로운 레시피 생성 처리
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

    //특정 레시피 조회
    @GetMapping ("/{recipeId}")
    public String getRecipeById(@PathVariable Long recipeId, Model model) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipes/detail";
    }

    //모든 레시피 조회
    @GetMapping
    public String getAllRecipes(Model model) {
        List <Recipe> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "recipes";
    }
}
