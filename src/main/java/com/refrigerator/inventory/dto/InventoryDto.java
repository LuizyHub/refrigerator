package com.refrigerator.inventory.dto;

import com.refrigerator.inventory.entity.Inventory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class InventoryDto {

    private Integer inventoryId;
    private Long refrigId;  // 냉장고 ID
    private Long itemId;  // 식재료 ID
    private Integer unitId;  // 단위 ID
    private Double amount;  // 수량
    private LocalDateTime endAt;  // 만료일 (optional)

    private InventoryDto(Integer inventoryId, Long refrigId, Long itemId, Integer unitId, Double amount, LocalDateTime endAt) {
        this.inventoryId = inventoryId;
        this.refrigId = refrigId;
        this.itemId = itemId;
        this.unitId = unitId;
        this.amount = amount;
        this.endAt = endAt;
    }

    public static InventoryDto fromEntity(Inventory inventory) {
        return new InventoryDto(
                inventory.getId(),
                inventory.getRefrigId(),
                inventory.getRefrigId(),
                inventory.getUnit().getUnitId(),
                inventory.getAmount(),
                inventory.getEndAt()
        );
    }
}
