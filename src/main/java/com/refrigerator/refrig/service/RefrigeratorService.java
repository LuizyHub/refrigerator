package com.refrigerator.refrig.service;

import com.refrigerator.common.helper.RecipeHelper;
import com.refrigerator.inventory.entity.Inventory;
import com.refrigerator.inventory.service.InventoryService;
import com.refrigerator.permission.entity.Permission;
import com.refrigerator.permission.service.MemberRefrigService;
import com.refrigerator.permission.service.PermissionService;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.service.RecipeService;
import com.refrigerator.refrig.dto.RefrigeratorCreateDto;
import com.refrigerator.refrig.entity.Refrigerator;
import com.refrigerator.refrig.repository.RefrigeratorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RefrigeratorService {
    private final RefrigeratorRepository refrigeratorRepository;
    private final MemberRefrigService memberRefrigService;
    private final PermissionService permissionService;
    private final RecipeService recipeService;
    private final InventoryService inventoryService;
    private final RecipeHelper recipeHelper;

    public void deleteRefrigerator(Long UserId, Long refrigId) {
        if (memberRefrigService.hasPermissionToDelete(UserId, refrigId)) {
            throw new IllegalArgumentException("해당 냉장고에 대한 권한이 없습니다.");
        }
        refrigeratorRepository.deleteById(refrigId);
    }

    public void createRefrigerator(Long userId, RefrigeratorCreateDto refrigeratorCreateDto) {
        Refrigerator save = refrigeratorRepository.save(refrigeratorCreateDto.toRefrigerator());

        Permission rwdPermission = permissionService.getRWDPermission();

        memberRefrigService.createMemberRefrigRWD(userId, save.getRefrigId(), rwdPermission);
    }

    public List<Refrigerator> getAllRefrigeratorsReadable(Long userId) {
        return memberRefrigService.getAllReadableRefrigerators(userId);
    }

    public Map<Recipe, Boolean> getRecipesByRefrigIdsWhereInventoryIsEnough(Long userId, Long refrigId) {
        List<Recipe> recipes = recipeService.getAllRecipesByUserId(userId);
        List<Inventory> inventories = inventoryService.getAllInventories(refrigId);

        return recipes.stream()
                .collect(Collectors.toMap(
                        recipe -> recipe,
                        recipe -> recipeHelper.hasEnoughIngredients(inventories, recipe.getRecipeId())
                ));
    }

    public Refrigerator getRefrigeratorById(Long refrigId) {
        return refrigeratorRepository.findById(refrigId)
                .orElseThrow(() -> new IllegalArgumentException("냉장고를 찾을 수 없습니다."));
    }

}
