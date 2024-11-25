package com.refrigerator.inventory.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InventoryResponseDto {

  private Integer inventoryId;
  private Long refrigId;
  private Long itemId;
  private String itemName;  // 식재료 이름
  private String unitName;  // 단위 이름
  private Double amount;  // 수량
  private String endAt;  // 만료일

  public InventoryResponseDto(Integer inventoryId, Long refrigId, Long itemId, String itemName, String unitName,
                              Double amount, String endAt) {
    this.inventoryId = inventoryId;
    this.refrigId = refrigId;
    this.itemId = itemId;
    this.itemName = itemName;
    this.unitName = unitName;
    this.amount = amount;
    this.endAt = endAt;
  }
}

