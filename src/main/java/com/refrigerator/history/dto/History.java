package com.refrigerator.history.dto;

public record History(Long id, long memberId, int inventoryId, int unitId, double amount) {
    public static History of(Long id, long memberId, int inventoryId, int unitId, double amount) {
        return new History(id, memberId, inventoryId, unitId, amount);
    }
}
