package com.refrigerator.item.service;

import com.refrigerator.item.dto.ItemCreateDto;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.repository.ItemCategoryRepository;
import com.refrigerator.item.repository.ItemRepository;
import com.refrigerator.state.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final StateRepository stateRepository;

    public ItemService(ItemRepository itemRepository, ItemCategoryRepository itemCategoryRepository, StateRepository stateRepository) {
        this.itemRepository = itemRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.stateRepository = stateRepository;
    }

    public void createItem(ItemCreateDto itemCreateDto) {
        var category = itemCategoryRepository.findById(itemCreateDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        var state = stateRepository.findById(itemCreateDto.getStateId())
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
}