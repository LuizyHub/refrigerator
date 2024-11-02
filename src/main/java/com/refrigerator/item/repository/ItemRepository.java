package com.refrigerator.item.repository;

import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.common.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByCategoryInAndState(List<ItemCategory> categories, State state);

    List<Item> findByCategoryIn(List<ItemCategory> categories);

    List<Item> findByState(State state);
}
