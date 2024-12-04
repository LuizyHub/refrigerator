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
                // 유통기한이 지난 재고는 제외
                if (inventory.getEndAt() != null && inventory.getEndAt().isBefore(LocalDateTime.now())) {
                    return acc;
                }
                if (inventory.getItem().getItemId().equals(item.getItemId())
                        && inventory.getUnit().getUnitId().equals(unit.getUnitId())) {
                    return acc + unitTransformService.transformUnit(unit.getUnitId(), inventory.getUnit().getUnitId(), inventory.getAmount());
                }
                return acc;
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
            double amount = ingredient.getAmount();

            for (Inventory inventory : inventories) {
                // 유통기한이 지난 재고는 제외
                if (inventory.getEndAt() != null && inventory.getEndAt().isBefore(LocalDateTime.now())) {
                    continue;
                }
                if (inventory.getItem().getItemId().equals(item.getItemId())
                        && inventory.getUnit().getUnitId().equals(unit.getUnitId())) {
                    double consumedAmount = unitTransformService.transformUnit(unit.getUnitId(), inventory.getUnit().getUnitId(), amount);
                    if (inventory.getAmount() >= consumedAmount) {
                        inventoryService.consumeInventory(inventory.getId(), consumedAmount);
                        amount -= consumedAmount;
                    } else {
                        inventoryService.consumeInventory(inventory.getId(), inventory.getAmount());
                        amount -= inventory.getAmount();
                    }
                }
            }
        }
    }

}
