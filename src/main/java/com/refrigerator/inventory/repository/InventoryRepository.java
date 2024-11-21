package com.refrigerator.inventory.repository;

import com.refrigerator.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 이 애너테이션은 필수는 아니지만 명시적으로 선언해도 무방
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

  List<Inventory> findByRefrigId(Long refrigId); // refrigId로 데이터를 검색하는 메서드

//  List<Inventory> findByRefrig_RefrigId(Long refrigId);
}



