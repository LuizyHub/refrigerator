package com.refrigerator.inventory.entity;

import com.refrigerator.item.entity.Item;
import com.refrigerator.refrig.entity.Refrigerator;
import com.refrigerator.unit.entity.Unit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Inventory", indexes = {
        @Index(name = "idx_refrig", columnList = "refrig_id"),
        @Index(name = "idx_inventory_end_at", columnList = "end_at ASC"),
        @Index(name = "idx_inventory_item_id", columnList = "item_id")
})
@Getter
@Setter
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId; // inventory_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refrig_id", nullable = false)
    private Refrigerator refrigerator; // refrig_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; // item_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit; // unit_id

    @Column(nullable = false)
    private Double amount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "end_at")
    private LocalDateTime endAt; // Optional
}
