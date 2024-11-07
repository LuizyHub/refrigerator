package com.refrigerator.unit.service;

import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.entity.UnitTransform;
import com.refrigerator.unit.entity.UnitTransformId;
import com.refrigerator.unit.repository.UnitTransformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UnitTransformService {

    private final UnitTransformRepository unitTransformRepository;

    public Double getUnitTransformRate(Unit fromUnit, Unit toUnit) {
        if (fromUnit.equals(toUnit)) {
            return 1.0;
        }
        /* state 필드가 추가되면 주석을 해제하세요.
        if (!Objects.equals(fromUnit.getState(), toUnit.getState())) {
            throw new IllegalArgumentException("같은 상태의 단위끼리만 변환할 수 있습니다.");
        }
        */
        return unitTransformRepository.findById(new UnitTransformId(fromUnit.getUnitId(), toUnit.getUnitId()))
                .map(UnitTransform::getRatio).orElseThrow(() -> new IllegalArgumentException("변환 비율이 존재하지 않습니다."));
    }

    public List<UnitTransform> getUnitTransforms(Unit fromUnit) {
        return unitTransformRepository.findByFromUnitId(fromUnit.getUnitId());
    }
}
