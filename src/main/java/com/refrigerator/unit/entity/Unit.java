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
    private Long unitId;  // 단위 ID

    @Column(nullable = false, unique = true)
    private String name;  // 단위 이름 (예: kg, g, oz 등)

    @Column(nullable = false)
    private Integer stateId;

//    @ManyToOne
//    @JoinColumn(name = "state_id", nullable = false)
//    private State state;


    public Unit(String name, Integer stateId) {
        this.name = name;
        this.stateId = stateId;
    }

    // Factory method (static 메서드로 객체 생성)
    public static Unit of(String name, Integer stateId) {
        return new Unit(name, stateId);
    }
}
