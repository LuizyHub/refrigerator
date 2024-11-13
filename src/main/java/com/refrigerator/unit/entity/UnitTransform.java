package com.refrigerator.unit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "unit_transform")
@IdClass(UnitTransformId.class)
public class UnitTransform {

    @Id
    @ManyToOne
    @JoinColumn(name = "from_unit_id", referencedColumnName = "unitId", nullable = false)
    private Unit fromUnit;

    @Id
    @ManyToOne
    @JoinColumn(name = "to_unit_id", referencedColumnName = "unitId", nullable = false)
    private Unit toUnit;

    @Column(nullable = false)
    private double ratio; // 변환 비율 (예: 1 kg = 1000 g)

    public UnitTransform(Unit fromUnit, Unit toUnit, double ratio) {
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.ratio = ratio;
    }

    public static UnitTransform of(Unit fromUnit, Unit toUnit, double ratio) {
        return new UnitTransform(fromUnit, toUnit, ratio);
    }
}
