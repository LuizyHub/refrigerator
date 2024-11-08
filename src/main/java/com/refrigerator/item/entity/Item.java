package com.refrigerator.item.entity;

import com.refrigerator.state.entity.State;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory category;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    public Item(String name, ItemCategory category, State state) {
        this.name = name;
        this.category = category;
        this.state = state;
    }
}