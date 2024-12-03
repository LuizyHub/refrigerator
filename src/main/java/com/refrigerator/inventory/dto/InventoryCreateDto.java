package com.refrigerator.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryCreateDto {

  @NotNull
  private Long refrigId;  // 냉장고 ID

  @NotNull
  private Long itemId;  // 식재료 ID

  @NotNull
  private Long unitId;  // 단위 ID

  @NotNull
  private Double amount;  // 수량

  private LocalDateTime endAt;  // 만료일 (optional)
}
