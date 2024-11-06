package com.refrigerator.state.repository;

import com.refrigerator.state.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
