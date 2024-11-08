package com.refrigerator.item.repository;

import com.refrigerator.item.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

    // name으로 ItemCategory 조회
    Optional<ItemCategory> findByName(String name);
}
