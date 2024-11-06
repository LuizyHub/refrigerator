package com.refrigerator.item.service;

import com.refrigerator.item.dto.ItemCreateDto;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.repository.ItemRepository;
import com.refrigerator.itemCategory.repository.ItemCategoryRepository;
import com.refrigerator.state.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Item createItem(ItemCreateDto itemCreateDto) {
        var category = itemCategoryRepository.findById(itemCreateDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        var state = stateRepository.findById(itemCreateDto.getStateId())
                .orElseThrow(() -> new EntityNotFoundException("State not found"));

        Item item = new Item();
        item.setName(itemCreateDto.getName());
        item.setCategory(category);
        item.setState(state);

        return itemRepository.save(item);
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}