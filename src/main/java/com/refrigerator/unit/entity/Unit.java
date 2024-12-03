package com.refrigerator.unit.entity;

import com.refrigerator.state.entity.State;
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
    private Integer unitId;  // 단위 ID

    @Column(nullable = false, unique = true)
    private String name;  // 단위 이름 (예: kg, g, oz 등)

    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 관계 설정, 지연 로딩
    @JoinColumn(name = "state_id", nullable = false)  // 외래 키 이름 설정
    private State state;

    public Unit(String name, State state) {
        this.name = name;
        this.state = state;
    }

    // Factory method (static 메서드로 객체 생성)
    public static Unit of(String name, State state) {
        return new Unit(name, state);
    }
}
