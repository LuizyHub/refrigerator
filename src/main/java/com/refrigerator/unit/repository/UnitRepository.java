package com.refrigerator.unit.repository;

import com.refrigerator.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.

    /* state 필드가 추가되면 주석을 해제하세요.
    List<Unit> findByState(State state);
    */
}
