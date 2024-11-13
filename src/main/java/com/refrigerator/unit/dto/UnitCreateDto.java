package com.refrigerator.unit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnitCreateDto {

  private String name;   // 단위 이름 (예: kg, g, oz 등)
  private Integer stateId; // 단위 기호
}
