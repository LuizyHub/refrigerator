// InventoryResponseDto.java
package com.refrigerator.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryResponseDto {
    private Integer inventoryId;
    private Long refrigId;
    private Integer itemId;
    private Integer unitId;
    private Double amount;
    private LocalDateTime createdAt; // Optional
    private LocalDateTime endAt;     // Optional
}