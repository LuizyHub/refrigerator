package com.refrigerator.item.service;

import com.refrigerator.item.dto.ItemCreateDto;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.repository.ItemRepository;
import com.refrigerator.itemCategory.repository.ItemCategoryRepository;
import com.refrigerator.state.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        var state = stateRepository.findById(itemCreateDto.getStateId())
                .orElseThrow(() -> new EntityNotFoundException("State not found"));

        // Use the toItem method from ItemCreateDto
        Item item = itemCreateDto.toItem(category, state);
        itemRepository.save(item);
    }
}