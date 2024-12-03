package com.refrigerator.unit.service;

import com.refrigerator.unit.dto.UnitTransformCreateDto;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.entity.UnitTransform;
import com.refrigerator.unit.entity.UnitTransformId;
import com.refrigerator.unit.repository.UnitRepository;
import com.refrigerator.unit.repository.UnitTransformRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitTransformService {

    private final UnitTransformRepository unitTransformRepository;
    private final UnitService unitServie;
    private final EntityManager entityManager;

    // 모든 UnitTransform 조회
    public List<UnitTransform> getAllUnitTransforms() {
        return unitTransformRepository.findAll();
    }

    // 특정 UnitTransform 조회
    public UnitTransform getUnitTransformById(Integer fromUnitId, Integer toUnitId) {
        return unitTransformRepository.findById(new UnitTransformId(fromUnitId, toUnitId))
            .orElseThrow(() -> new IllegalArgumentException("Unit transform not found"));
    }

    // 새로운 UnitTransform 생성
    public UnitTransform createUnitTransform(UnitTransformCreateDto createDto) {
        // id를 가지고 ManyToOne Unit 객체 조회
        Unit savedFromUnit = unitServie.getUnitById(createDto.getFromUnitId());
        Unit savedToUnit = unitServie.getUnitById(createDto.getToUnitId());

        UnitTransform unitTransform = UnitTransform.of(savedFromUnit, savedToUnit, createDto.getRatio());
        return unitTransformRepository.save(unitTransform);
    }

    // 유닛 변환 삭제
    public void deleteUnitTransform(Integer fromUnitId, Integer toUnitId) {
        unitTransformRepository.deleteById(new UnitTransformId(fromUnitId, toUnitId));
    }

    public double transformUnit(Integer fromUnitId, Integer toUnitId, double value) {
        if (fromUnitId.equals(toUnitId)) {
            return value;
        }
        Unit fromUnit = entityManager.getReference(Unit.class, fromUnitId);
        Unit toUnit = entityManager.getReference(Unit.class, toUnitId);
        UnitTransform unitTransform = unitTransformRepository.findByFromUnitAndToUnit(fromUnit, toUnit)
                .orElseThrow(() -> new IllegalArgumentException("Unit transform not found"));

        return value * unitTransform.getRatio();
    }
}
