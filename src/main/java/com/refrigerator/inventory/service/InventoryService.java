package com.refrigerator.inventory.service;

import com.refrigerator.common.exception.UnauthorizedException;
import com.refrigerator.history.dto.History;
import com.refrigerator.history.service.HistoryService;
import com.refrigerator.inventory.entity.Inventory;
import com.refrigerator.inventory.repository.InventoryRepository;
import com.refrigerator.inventory.dto.InventoryCreateDto;
import com.refrigerator.inventory.dto.InventoryResponseDto;
import com.refrigerator.item.entity.Item;
import com.refrigerator.permission.service.MemberRefrigService;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.service.UnitTransformService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final MemberRefrigService memberRefrigService;
    private final InventoryRepository inventoryRepository;
    private final HistoryService historyService;
    private final EntityManager entityManager;
    private final UnitTransformService unitTransformService;

    /**
     * 재고 목록 전체 조회
     *
     * @param userId
     * @param refrigId
     * @return
     */
    public List<InventoryResponseDto> getAllInventoriesWithPermission(Long userId, Long refrigId) {
        if (!memberRefrigService.hasPermissionToRead(userId, refrigId)) {
            throw new UnauthorizedException("해당 냉장고에 대한 읽기 권한이 없습니다.");
        }

        List<Inventory> inventories = getAllInventories(refrigId);

        List<InventoryResponseDto> inventoryDtoList = new ArrayList<>();
        // 재고 목록 엔티티 to dto
        for (Inventory inventory : inventories) {
            InventoryResponseDto inventoryDto = new InventoryResponseDto(inventory.getId()
                    , inventory.getRefrigId()
                    , inventory.getItem().getItemId()
                    , inventory.getItem().getName()
                    , inventory.getUnit().getName()
                    , inventory.getAmount()
                    , inventory.getEndAt() != null ? inventory.getEndAt().toString() : null);

            // responseDto add
            inventoryDtoList.add(inventoryDto);
        }
        return inventoryDtoList;
    }

    public List<Inventory> getAllInventories(Long refrigId) {
        // 재고 목록 조회
        List<Inventory> inventories = inventoryRepository.findByRefrigIdOrderByEndAtAsc(refrigId);

        // 유통기한 임박순으로 정렬, null값은 가장 마지막으로
        int idx;

        for (idx = inventories.size() - 1; idx >= 0; idx--) {
            if (inventories.get(idx).getEndAt() == null) {
                break;
            }
        }

        if (idx != -1) {
            List<Inventory> list = new ArrayList<>();
            list.addAll(inventories.subList(idx + 1, inventories.size()));
            list.addAll(inventories.subList(0, idx + 1));

            inventories = list;
        }

        return inventories;
    }

    public void addInventory(Long userId, InventoryCreateDto createDto) {
        if (!memberRefrigService.hasPermissionToWrite(userId, createDto.getRefrigId())) {
            throw new UnauthorizedException("해당 냉장고에 대한 쓰기 권한이 없습니다.");
        }

        Item item = entityManager.getReference(Item.class, createDto.getItemId());
        Unit unit = entityManager.getReference(Unit.class, createDto.getUnitId());

        Inventory inventory = new Inventory(
                createDto.getRefrigId(),
                item,
                unit,
                createDto.getAmount(),
                createDto.getEndAt()
        );

        Inventory save = inventoryRepository.save(inventory);
        if (save == null) {
            throw new RuntimeException("식재료 추가에 실패했습니다.");
        }

        historyService.addLog(new History(userId, userId, save.getId(), save.getUnit().getUnitId(), save.getAmount()));
    }

    public Inventory getInventoryById(Long userId, Integer inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found"));

        if (!memberRefrigService.hasPermissionToRead(userId, inventory.getRefrigId())) {
            throw new UnauthorizedException("해당 냉장고에 대한 읽기 권한이 없습니다.");
        }

        return inventory;
    }

    public void consumeInventoryWithPermission(Long userId, Integer inventoryId, Double amount, Integer unitId) {
        if (!memberRefrigService.hasPermissionToWrite(userId, getInventoryById(userId, inventoryId).getRefrigId())) {
            throw new UnauthorizedException("해당 냉장고에 대한 쓰기 권한이 없습니다.");
        }
        Inventory inventory = getInventoryById(userId, inventoryId);
        double consumedAmount = unitTransformService.transformUnit(inventory.getUnit().getUnitId(), unitId, amount);
        inventory.consume(consumedAmount);

        inventoryRepository.save(inventory);
        historyService.addLog(new History(null, null, inventoryId, inventory.getUnit().getUnitId(), -consumedAmount));
    }

    public double consumeInventory(Integer inventoryId, double amount) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found"));
        double consume = inventory.consume(amount);
        inventoryRepository.save(inventory);
        historyService.addLog(new History(null, null, inventoryId, inventory.getUnit().getUnitId(), -consume));
        return consume;
    }

    private String getItemName(Long itemId) {
        return "Dummy Item";
    }

    private String getUnitName(Long unitId) {
        return "Dummy Unit";
    }

    public void deleteInventoryWithPermission(Long userId, Integer inventoryId) {
        Inventory inventory = getInventoryById(userId, inventoryId);
        if (!memberRefrigService.hasPermissionToDelete(userId, inventory.getRefrigId())) {
            throw new UnauthorizedException("해당 냉장고에 대한 삭제 권한이 없습니다.");
        }
        inventoryRepository.deleteById(inventoryId);
    }
}
