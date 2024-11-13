package com.refrigerator.unit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA에서 기본 생성자는 protected로 처리
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;  // 단위 ID

    @Column(nullable = false, unique = true)
    private String name;  // 단위 이름 (예: kg, g, oz 등)

    @Column(nullable = false)
    private String symbol;  // 단위 기호 (예: kg -> "kg", g -> "g" 등)

    // 생성자 (protected 접근 제한자로, 외부에서 직접 생성하지 못하게 함)
    private Unit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    // Factory method (static 메서드로 객체 생성)
    public static Unit of(String name, String symbol) {
        return new Unit(name, symbol);
    }
}
