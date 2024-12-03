package com.refrigerator.item.service;

import com.refrigerator.item.dto.ItemCreateDto;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.item.repository.ItemCategoryRepository;
import com.refrigerator.item.repository.ItemRepository;
import com.refrigerator.state.entity.State;
import com.refrigerator.state.repository.StateRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final StateRepository stateRepository;
    private final EntityManager entityManager;

    public void createItem(ItemCreateDto itemCreateDto) {
        ItemCategory category = itemCategoryRepository.findById(itemCreateDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        State state = stateRepository.findById(itemCreateDto.getStateId())
                .orElseThrow(() -> new IllegalArgumentException("State not found"));

        Item item = itemCreateDto.toItem(category, state);
        itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
    }

    public List<Item> getItemsByCategoryId(List<Long> categoryIds) {
        List<ItemCategory> list = categoryIds.stream().map(id -> entityManager.getReference(ItemCategory.class, id)).toList();
        return itemRepository.findByCategoryIn(list);
    }
}

