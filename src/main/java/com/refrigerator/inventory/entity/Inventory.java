package com.refrigerator.inventory.entity;

import com.refrigerator.item.entity.Item;
import com.refrigerator.refrig.entity.Refrigerator;
import com.refrigerator.unit.entity.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "INVENTORY")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVENTORY_ID")
    private Integer id;

    @Column(name = "REFRIG_ID")
    private Long refrigId;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "UNIT_ID", nullable = false)
    private Unit unit;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "END_AT")
    private LocalDateTime endAt;

    public Inventory(Long refrigId, Item item, Unit unit, Double amount, LocalDateTime localDateTime) {
        this.refrigId = refrigId;
        this.item = item;
        this.unit = unit;
        this.amount = amount;
        this.endAt = localDateTime;
    }

    protected Inventory() {

    }
}