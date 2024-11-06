package com.refrigerator.permission.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false)
    private boolean readable;

    @Column(nullable = false)
    private boolean writable;

    @Column(nullable = false)
    private boolean deletable;

    public Permission(String name, boolean readable, boolean writable, boolean deletable) {
        this.name = name;
        this.readable = readable;
        this.writable = writable;
        this.deletable = deletable;
    }
}