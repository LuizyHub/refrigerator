package com.refrigerator.unit.repository;

import com.refrigerator.unit.entity.Unit;
import com.refrigerator.common.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
    List<Unit> findByState(State state);
}
