package com.refrigerator.itemCategory.repository;

import com.refrigerator.itemCategory.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
}