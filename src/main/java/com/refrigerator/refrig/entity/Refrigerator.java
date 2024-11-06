package com.refrigerator.refrig.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refrigerator")
public class Refrigerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refrigId;

    @Column(nullable = false, length = 100)
    private String name;

    // to be implemented
//    private Set<Inventory> inventories = new HashSet<>();

    private Refrigerator(String name) {
        this.name = name;
    }

    public static Refrigerator of(String name) {
        return new Refrigerator(name);
    }
}