package com.refrigerator.unit.service;

import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.entity.UnitTransform;
import com.refrigerator.unit.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;
    private final UnitTransformService unitTransformService;

    public double transferUnit(Unit fromUnit, Unit toUnit, double amount) {
        return amount * unitTransformService.getUnitTransformRate(fromUnit, toUnit);
    }

    /**
     * 변환될 수 있는 단위 목록을 반환합니다.
     * @param fromUnit
     * @return List<Unit>
     */
    public List<Unit> getUnitsToTransform(Unit fromUnit) {
        return unitTransformService.getUnitTransforms(fromUnit).stream()
                .map(UnitTransform::getToUnit)
                .collect(Collectors.toList());
    }

    /* state 필드가 추가되면 주석을 해제하세요.
    public List<Unit> getAllUnitsByState(State state) {
        return unitRepository.findByState(state);
    }
    */

    /**
     * 단위 ID로 단위를 조회합니다.
     * @param unitId
     * @return Unit
     */
    public Unit getUnitById(Integer unitId) {
        return unitRepository.findById(unitId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 단위입니다."));
    }

}
