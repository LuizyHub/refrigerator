package com.refrigerator.unit.service;

import com.refrigerator.state.entity.State;
import com.refrigerator.state.repository.StateRepository;
import com.refrigerator.unit.dto.UnitCreateDto;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.repository.UnitRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;
    private final EntityManager entityManager;

    // 모든 Unit을 조회하는 메서드
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();  // UnitRepository에서 모든 Unit을 반환
    }

/*
    // Unit을 생성하는 메서드
    public Unit createUnit(UnitCreateDto unitCreateDto) {
        // UnitCreateDto로부터 단위 정보 받아서 새로운 Unit 생성
        Unit unit = Unit.of(unitCreateDto.getName(), unitCreateDto.getStateId());
        return unitRepository.save(unit);  // Unit 저장 후 반환
    }
*/

    // 특정 id에 해당하는 Unit을 조회하는 메서드
    public Unit getUnitById(Integer id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unit not found"));  // Unit을 못 찾을 경우 예외 발생
    }

    // 특정 Unit 삭제하는 메서드
    public void deleteUnit(Integer id) {
        unitRepository.deleteById(id);  // UnitRepository에서 id로 Unit 삭제
    }

    public List<Unit> getUnitsByState(State state) {
        return unitRepository.findByState(state);
    }

    public List<Unit> getUnitsByStateId(Integer stateId) {
        State state = entityManager.getReference(State.class, stateId);
        return getUnitsByState(state);
    }
}
