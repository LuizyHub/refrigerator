package com.refrigerator.unit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Unit_Transform")
@IdClass(UnitTransformId.class)
@Getter
@Setter
@NoArgsConstructor
public class UnitTransform {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_unit_id", nullable = false)
    private Unit fromUnit; // from_unit_id

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_unit_id", nullable = false)
    private Unit toUnit; // to_unit_id

    @Column(nullable = false)
    private Double ratio;
}
