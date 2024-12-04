package com.refrigerator.history.dto;

public record History(Long id, Long memberId, int inventoryId, int unitId, double amount) {
    public static History of(Long id, Long memberId, int inventoryId, int unitId, double amount) {
        return new History(id, memberId, inventoryId, unitId, amount);
    }

    public History withMemberId(Long memberId) {
        return new History(id, memberId, inventoryId, unitId, amount);
    }
}
