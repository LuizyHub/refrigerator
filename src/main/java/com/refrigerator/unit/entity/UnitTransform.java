package com.refrigerator.unit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "unit_transform")
public class UnitTransform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitTransformId;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;  // 참조하는 Unit 엔티티

    @Column(nullable = false)
    private double conversionRate; // 변환 비율 (예: 1 kg = 1000 g)

    // 생성자
    public UnitTransform(Unit unit, double conversionRate) {
        this.unit = unit;
        this.conversionRate = conversionRate;
    }
}
