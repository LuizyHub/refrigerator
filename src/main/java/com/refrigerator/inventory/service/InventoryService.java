package com.refrigerator.inventory.service;

import com.refrigerator.common.exception.UnauthorizedException;
import com.refrigerator.history.dto.History;
import com.refrigerator.history.service.HistoryService;
import com.refrigerator.inventory.entity.Inventory;
import com.refrigerator.inventory.repository.InventoryRepository;
import com.refrigerator.inventory.dto.InventoryCreateDto;
import com.refrigerator.inventory.dto.InventoryResponseDto;
import com.refrigerator.permission.service.MemberRefrigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

  private final MemberRefrigService memberRefrigService;
  private final InventoryRepository inventoryRepository;
  private final HistoryService historyService;

  /**
   * 재고 목록 전체 조회
   *
   * @param userId
   * @param refrigId
   * @return
   */
  public List<InventoryResponseDto> getAllInventories(Long userId, Long refrigId) {
    if (!memberRefrigService.hasPermissionToRead(userId, refrigId)) {
      throw new UnauthorizedException("해당 냉장고에 대한 읽기 권한이 없습니다.");
    }

    // 재고 목록 조회
    List<Inventory> inventories = inventoryRepository.findByRefrigId(refrigId);

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

  public void addInventory(Long userId, InventoryCreateDto createDto) {
    if (!memberRefrigService.hasPermissionToWrite(userId, createDto.getRefrigId())) {
      throw new UnauthorizedException("해당 냉장고에 대한 쓰기 권한이 없습니다.");
    }

    Inventory inventory = new Inventory(
        createDto.getRefrigId(),
        createDto.getItemId(),
        createDto.getUnitId(),
        createDto.getAmount(),
        createDto.getEndAt() != null ? LocalDateTime.parse(createDto.getEndAt()) : null
    );

    Inventory save = inventoryRepository.save(inventory);
    if (save == null) {
      throw new RuntimeException("식재료 추가에 실패했습니다.");
    }

    historyService.addLog(new History(userId, save.getId(), save.getId(), save.getUnit().getUnitId(), save.getAmount()));
  }

  private String getItemName(Long itemId) {
    return "Dummy Item";
  }

  private String getUnitName(Long unitId) {
    return "Dummy Unit";
  }
}
