package com.refrigerator.history.entity;
import com.refrigerator.inventory.entity.Inventory;
import com.refrigerator.member.entity.Member;
import com.refrigerator.unit.entity.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID")
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "INVENTORY_ID")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID", nullable = false)
    private Unit unit;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    // 생성자
    private Log(Member member, Inventory inventory, Unit unit, Double amount, LocalDateTime timestamp) {
        this.member = member;
        this.inventory = inventory;
        this.unit = unit;
        this.amount = amount;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    // 정적 팩토리 메서드
    public static Log of(Member member, Inventory inventory, Unit unit, Double amount, LocalDateTime timestamp) {
        return new Log(member, inventory, unit, amount, timestamp);
    }
}
