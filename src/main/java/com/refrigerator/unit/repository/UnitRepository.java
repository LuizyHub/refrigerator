package com.refrigerator.unit.repository;

import com.refrigerator.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
  // 기본적인 CRUD 메서드는 JpaRepository가 제공
}
