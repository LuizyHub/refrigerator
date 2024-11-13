package com.refrigerator.unit.service;

import com.refrigerator.unit.dto.UnitCreateDto;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

  private final UnitRepository unitRepository;

  // 생성자 주입을 통한 UnitRepository 의존성 주입
  public UnitService(UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
  }

  // 모든 Unit을 조회하는 메서드
  public List<Unit> getAllUnits() {
    return unitRepository.findAll();  // UnitRepository에서 모든 Unit을 반환
  }

  // Unit을 생성하는 메서드
  public Unit createUnit(UnitCreateDto unitCreateDto) {
    // UnitCreateDto로부터 단위 정보 받아서 새로운 Unit 생성
    Unit unit = Unit.of(unitCreateDto.getName(), unitCreateDto.getSymbol());
    return unitRepository.save(unit);  // Unit 저장 후 반환
  }

  // 특정 id에 해당하는 Unit을 조회하는 메서드
  public Unit getUnitById(Long id) {
    return unitRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Unit not found"));  // Unit을 못 찾을 경우 예외 발생
  }

  // 특정 Unit 삭제하는 메서드
  public void deleteUnit(Long id) {
    unitRepository.deleteById(id);  // UnitRepository에서 id로 Unit 삭제
  }
}
