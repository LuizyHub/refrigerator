package com.refrigerator.inventory.repository;

import com.refrigerator.inventory.entity.Inventory;
import com.refrigerator.item.entity.Item;
import com.refrigerator.refrig.entity.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findByRefrigerator(Refrigerator refrigerator);

    List<Inventory> findByRefrigeratorOrderByEndAtAsc(Refrigerator refrigerator);

    List<Inventory> findByRefrigeratorAndItem_Category_CategoryId(Refrigerator refrigerator, Integer categoryId);
}
