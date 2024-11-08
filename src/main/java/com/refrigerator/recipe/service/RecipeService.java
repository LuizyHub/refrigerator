package com.refrigerator.recipe.service;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.member.entity.Member;
import com.refrigerator.member.repository.MemberRepository;
import com.refrigerator.recipe.dto.RecipeCreateDto;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.repository.RecipeRepository;
import com.refrigerator.recipeCategory.repository.RecipeCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCategoryRepository recipeCategoryRepository;
    private final MemberRepository memberRepository;

    public RecipeService(RecipeRepository recipeRepository, RecipeCategoryRepository recipeCategoryRepository, MemberRepository memberRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeCategoryRepository = recipeCategoryRepository;
        this.memberRepository = memberRepository;
    }

    public Recipe createRecipe(@CurrentMember Member member, RecipeCreateDto recipeCreateDto) {
        Recipe recipe = new Recipe();
        recipe.setName(member.getName());
        recipe.setMember(member);

        return recipeRepository.save(recipe);
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

}


//public Item getItemById(Long id) {
//    return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));
//}
//
//public List<Item> getAllItems() {
//    return itemRepository.findAll();
//}
//}