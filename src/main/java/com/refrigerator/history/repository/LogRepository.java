package com.refrigerator.history.repository;

import com.refrigerator.history.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByInventory_RefrigIdOrderByTimestampAsc(Long refrigId);

}
