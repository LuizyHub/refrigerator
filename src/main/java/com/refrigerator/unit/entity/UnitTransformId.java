package com.refrigerator.unit.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UnitTransformId implements Serializable {

    private Integer fromUnitId;
    private Integer toUnitId;

    public UnitTransformId() {}

    public UnitTransformId(Integer fromUnitId, Integer toUnitId) {
        this.fromUnitId = fromUnitId;
        this.toUnitId = toUnitId;
    }

    // Getters, Setters, equals, and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitTransformId that = (UnitTransformId) o;
        return Objects.equals(fromUnitId, that.fromUnitId) && Objects.equals(toUnitId, that.toUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromUnitId, toUnitId);
    }
}
