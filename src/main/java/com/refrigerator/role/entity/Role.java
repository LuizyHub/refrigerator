package com.refrigerator.role.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Role")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId; // role_id

    @Column(nullable = false, unique = true, length = 50)
    private String name; // "RWD", "RW", "R", "D"

    @Column(nullable = false)
    private Boolean readable;

    @Column(nullable = false)
    private Boolean writable;

    @Column(nullable = false)
    private Boolean deletable;
}
