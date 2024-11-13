package com.refrigerator.inventory.dto;

import lombok.Getter;

@Getter
public class InventoryResponseDto {

  private final Long inventoryId;
  private final Long refrigId;  private final Long itemId;
  private final String itemName;  // 식재료 이름
  private final String unitName;  // 단위 이름
  private final Double amount;  // 수량
  private final String createdAt;  // 등록일
  private final String endAt;  // 만료일

  public InventoryResponseDto(Long inventoryId, Long refrigId, Long itemId, String itemName, String unitName,
                              Double amount, String createdAt, String endAt) {
    this.inventoryId = inventoryId;
    this.refrigId = refrigId;
    this.itemId = itemId;
    this.itemName = itemName;
    this.unitName = unitName;
    this.amount = amount;
    this.createdAt = createdAt;
    this.endAt = endAt;
  }
}

