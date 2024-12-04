package com.refrigerator.refrig.controller;

import com.refrigerator.common.helper.RecipeHelper;
import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.member.entity.Member;
import com.refrigerator.permission.service.MemberRefrigService;
import com.refrigerator.refrig.dto.RefrigeratorCreateDto;
import com.refrigerator.refrig.entity.Refrigerator;
import com.refrigerator.refrig.service.RefrigeratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/refrigerators")
@RequiredArgsConstructor
public class RefrigeratorController {
    private final RefrigeratorService refrigeratorService;
    private final RecipeHelper recipeHelper;
    private final MemberRefrigService memberRefrigService;

    @GetMapping
    public String getRefrigerators(
            Model model,
            @CurrentMember Member member
    ) {
        List<Refrigerator> refrigerators = refrigeratorService.getAllRefrigeratorsReadable(member.getUserId());
        model.addAttribute("refrigerators", refrigerators);
        return "refrigerators";
    }

    @GetMapping("/new")
    public String createRefrigerator(
            @ModelAttribute("refrigerator") RefrigeratorCreateDto refrigeratorCreateDto
    ) {
        return "refrigerators/new";
    }

    @PostMapping("/new")
    public String createRefrigerator(
            @CurrentMember Member member,
            @Valid @ModelAttribute("refrigerator") RefrigeratorCreateDto refrigeratorCreateDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "refrigerators/new";
        }

        refrigeratorService.createRefrigerator(member.getUserId(), refrigeratorCreateDto);
        return "redirect:/refrigerators";
    }

    @GetMapping("/{refrigId}/consume/recipe/{recipeId}")
    public String consumeRecipe(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            @PathVariable Long recipeId

    ) {
        if (!memberRefrigService.hasPermissionToWrite(member.getUserId(), refrigId)) {
            throw new IllegalArgumentException("No permission");
        }

        recipeHelper.consumeIngredients(refrigId, recipeId);
        return "redirect:/refrigerators/" + refrigId + "/inventories";
    }
}
