package com.refrigerator.unit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "unit_transform")
public class UnitTransform {

    @EmbeddedId
    private UnitTransformId id;

    @ManyToOne
    @MapsId("fromUnitId")
    @JoinColumn(name = "from_unit_id", nullable = false)
    private Unit fromUnit;

    @ManyToOne
    @MapsId("toUnitId")
    @JoinColumn(name = "to_unit_id", nullable = false)
    private Unit toUnit;

    @Column(nullable = false)
    private Double ratio;
}
