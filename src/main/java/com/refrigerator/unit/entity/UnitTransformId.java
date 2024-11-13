package com.refrigerator.unit.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Embeddable
@EqualsAndHashCode
public class UnitTransformId {

    private Long unitId;
    private Long targetUnitId;

    // 기본 생성자
    public UnitTransformId() {}

    // 생성자
    public UnitTransformId(Long unitId, Long targetUnitId) {
        this.unitId = unitId;
        this.targetUnitId = targetUnitId;
    }
}
