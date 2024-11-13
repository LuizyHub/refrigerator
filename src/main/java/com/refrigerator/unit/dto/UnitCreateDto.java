package com.refrigerator.unit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnitCreateDto {

  private String name;   // 단위 이름 (예: kg, g, oz 등)
  private String symbol; // 단위 기호 (예: kg -> "kg", g -> "g" 등)
}
