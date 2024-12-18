package com.refrigerator.state.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)  // JPA 사용을 위한 기본 생성자 (protected)
@Table(name = "state")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stateId;

    @Column(nullable = false, unique = true)
    private String name;


    private State(String name) {
        this.name = name;
    }

    public static State of(String name) {
        return new State(name);
    }

}