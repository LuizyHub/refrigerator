package com.refrigerator.recipe.controller;

import com.refrigerator.common.resolver.CurrentMember;
//import com.refrigerator.item.entity.Item;
import com.refrigerator.member.dto.MemberLoginDto;
import com.refrigerator.member.entity.Member;
import com.refrigerator.recipe.dto.RecipeCreateDto;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public Recipe createRecipe(@RequestBody @CurrentMember Member member, RecipeCreateDto recipeCreateDto) {
        return recipeService.createRecipe(member, recipeCreateDto);
    }

    @GetMapping ("/{recipeId}")
    public Recipe getRecipe(@PathVariable Long recipeId) {
        return recipeService.getRecipeById(recipeId);
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {return recipeService.getAllRecipes();}
}
