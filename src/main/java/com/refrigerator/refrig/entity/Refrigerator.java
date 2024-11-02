package com.refrigerator.refrig.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Refrigerator")
@Getter
@Setter
@NoArgsConstructor
public class Refrigerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refrigId; // refrig_id

    @Column(nullable = false, length = 100)
    private String name;
}
