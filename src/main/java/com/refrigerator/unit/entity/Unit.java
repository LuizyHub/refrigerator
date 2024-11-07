package com.refrigerator.unit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "unit")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer unitId;

    @Column(nullable = false, length = 50)
    private String name;

//    @ManyToOne
//    @JoinColumn(name = "state_id", nullable = false)
//    private State state;

    // Getters and Setters
}
