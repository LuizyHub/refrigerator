package com.refrigerator.item.repository;

import com.refrigerator.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategory_CategoryId(Long categoryId);
}