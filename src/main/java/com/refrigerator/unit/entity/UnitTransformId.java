package com.refrigerator.unit.entity;

import java.io.Serializable;
import java.util.Objects;

public class UnitTransformId implements Serializable {

    private Integer fromUnit;
    private Integer toUnit;

    // Default constructor, equals, and hashCode methods

    public UnitTransformId() {
    }

    public UnitTransformId(Integer fromUnit, Integer toUnit) {
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitTransformId that = (UnitTransformId) o;
        return Objects.equals(fromUnit, that.fromUnit) && Objects.equals(toUnit, that.toUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromUnit, toUnit);
    }
}
