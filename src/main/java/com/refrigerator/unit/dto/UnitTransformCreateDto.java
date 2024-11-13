package com.refrigerator.unit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnitTransformCreateDto {

  private Integer fromUnitId; // from 유닛 아이디
  private Integer toUnitId;  // to 유닛 아이디

  private double ratio; // 변환 비율
}
