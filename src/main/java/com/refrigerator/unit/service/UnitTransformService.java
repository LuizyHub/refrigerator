package com.refrigerator.unit.service;

import com.refrigerator.unit.entity.UnitTransform;
import com.refrigerator.unit.entity.UnitTransformId;
import com.refrigerator.unit.repository.UnitTransformRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitTransformService {

    private final UnitTransformRepository unitTransformRepository;

    public UnitTransformService(UnitTransformRepository unitTransformRepository) {
        this.unitTransformRepository = unitTransformRepository;
    }

    // 모든 UnitTransform 조회
    public List<UnitTransform> getAllUnitTransforms() {
        return unitTransformRepository.findAll();
    }

    // 특정 UnitTransform 조회
    public UnitTransform getUnitTransformById(Long unitId, Long targetUnitId) {
        return unitTransformRepository.findById(new UnitTransformId(unitId, targetUnitId))
            .orElseThrow(() -> new IllegalArgumentException("Unit transform not found"));
    }

    // 새로운 UnitTransform 생성
    public UnitTransform createUnitTransform(UnitTransform unitTransform) {
        return unitTransformRepository.save(unitTransform);
    }
}
