package com.refrigerator.item.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "State")
@Getter
@Setter
@NoArgsConstructor
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId; // state_id

    @Column(nullable = false, unique = true, length = 50)
    private String name; // "Solid", "Liquid", etc.
}
