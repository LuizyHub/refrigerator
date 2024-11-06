package com.refrigerator.item.entity;

import com.refrigerator.itemCategory.entity.ItemCategory;
import com.refrigerator.state.entity.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
