package com.refrigerator.common.helper;

import com.refrigerator.inventory.entity.Inventory;
import com.refrigerator.inventory.service.InventoryService;
import com.refrigerator.item.entity.Item;
import com.refrigerator.recipeingredient.entity.RecipeIngredient;
import com.refrigerator.recipeingredient.service.RecipeIngredientService;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.service.UnitTransformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeHelper {

    private final InventoryService inventoryService;
    private final RecipeIngredientService recipeIngredientService;
    private final UnitTransformService unitTransformService;

    public boolean hasEnoughIngredients(Long refrigId, Long recipeId) {
        List<Inventory> inventories = inventoryService.getAllInventories(refrigId);
        List<RecipeIngredient> ingredients = recipeIngredientService.getIngredientsByRecipeId(recipeId);

        return hasEnoughIngredients(inventories, ingredients);
    }

    public boolean hasEnoughIngredients(List<Inventory> inventories, Long recipeId) {
        List<RecipeIngredient> ingredients = recipeIngredientService.getIngredientsByRecipeId(recipeId);

        return hasEnoughIngredients(inventories, ingredients);
    }

    public boolean hasEnoughIngredients(Long refrigId, List<RecipeIngredient> ingredients) {
        List<Inventory> inventories = inventoryService.getAllInventories(refrigId);

        return hasEnoughIngredients(inventories, ingredients);
    }

    private boolean hasEnoughIngredients(List<Inventory> inventories, List<RecipeIngredient> ingredients) {
        for (RecipeIngredient ingredient : ingredients) {
            Item item = ingredient.getItem();
            Unit unit = ingredient.getUnit();
            double amount = inventories.stream().reduce(0.0, (acc, inventory) -> {
                // 같은 아이템만 계산
                if (!inventory.getItem().getItemId().equals(item.getItemId())) {
                    return acc;
                }

                // 유통기한이 지난 재고는 제외
                if (inventory.getEndAt() != null && inventory.getEndAt().isBefore(LocalDateTime.now())) {
                    return acc;
                }

                // ingredient의 단위로 변환
                return acc + unitTransformService.transformUnit(inventory.getUnit().getUnitId(), unit.getUnitId(), inventory.getAmount());
            }, Double::sum);

            if (amount < ingredient.getAmount()) {
                return false;
            }
        }

        return true;
    }

    public void consumeIngredients(Long refrigId, Long recipeId) {
        List<Inventory> inventories = inventoryService.getAllInventories(refrigId);
        List<RecipeIngredient> ingredients = recipeIngredientService.getIngredientsByRecipeId(recipeId);

        consumeIngredients(inventories, ingredients);
    }

    private void consumeIngredients(List<Inventory> inventories, List<RecipeIngredient> ingredients) {
        // 유통기한 임박 순으로 정렬
        inventories.sort((i1, i2) -> {
            if (i1.getEndAt() == null && i2.getEndAt() == null) {
                return 0;
            } else if (i1.getEndAt() == null) {
                return 1;
            } else if (i2.getEndAt() == null) {
                return -1;
            }
            return i1.getEndAt().compareTo(i2.getEndAt());
        });

        for (RecipeIngredient ingredient : ingredients) {
            Item item = ingredient.getItem();
            Unit unit = ingredient.getUnit();

            // 사용해야할 양
            double amount = ingredient.getAmount();

            for (Inventory inventory : inventories) {
                // 같은 아이템만 계산
                if (!inventory.getItem().getItemId().equals(item.getItemId())) {
                    continue;
                }

                // 유통기한이 지난 재고는 제외
                if (inventory.getEndAt() != null && inventory.getEndAt().isBefore(LocalDateTime.now())) {
                    continue;
                }

                // ingredient의 단위로 변환
                double consumedAmount = unitTransformService.transformUnit(inventory.getUnit().getUnitId(), unit.getUnitId(), inventory.getAmount());

                // 재고 소비
                if (consumedAmount < amount) { // 현재 재고로 부족한 경우
                    inventoryService.consumeInventory(inventory.getId(), inventory.getAmount());
                }
                else { // 현재 재고로 충분한 경우
                    // 필요한 만큼만 소비
                    consumedAmount = unitTransformService.transformUnit(unit.getUnitId(), inventory.getUnit().getUnitId(), amount);
                    inventoryService.consumeInventory(inventory.getId(), consumedAmount);
                    amount = 0;
                    break;
                }
            }

            if (amount > 0) {
                throw new IllegalArgumentException("재고가 부족합니다.");
            }
        }
    }

}
