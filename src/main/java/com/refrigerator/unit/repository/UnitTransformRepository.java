package com.refrigerator.unit.repository;

import com.refrigerator.unit.entity.UnitTransform;
import com.refrigerator.unit.entity.UnitTransformId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitTransformRepository extends JpaRepository<UnitTransform, UnitTransformId> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.

    List<UnitTransform> findByFromUnitId(Integer fromUnitId);
}
