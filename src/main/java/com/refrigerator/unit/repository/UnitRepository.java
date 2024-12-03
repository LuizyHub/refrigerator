package com.refrigerator.unit.repository;

import com.refrigerator.state.entity.State;
import com.refrigerator.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
  // 기본적인 CRUD 메서드는 JpaRepository가 제공

    // name으로 Unit 조회
    List<Unit> findByState(State state);
}
