package com.refrigerator.unit.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
public class UnitTransformId implements Serializable {

    private Integer fromUnit;
    private Integer toUnit;

    // 기본 생성자
    public UnitTransformId() {}

    // 생성자
    public UnitTransformId(Integer fromUnitId, Integer toUnitId) {
        this.fromUnit = fromUnitId;
        this.toUnit = toUnitId;
    }
}
