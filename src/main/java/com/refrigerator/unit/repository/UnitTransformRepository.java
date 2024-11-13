package com.refrigerator.unit.repository;

import com.refrigerator.unit.entity.UnitTransform;
import com.refrigerator.unit.entity.UnitTransformId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitTransformRepository extends JpaRepository<UnitTransform, UnitTransformId> {
    // 변환 비율 관련 추가 메서드들이 필요할 수 있음
}
