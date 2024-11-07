package com.refrigerator.item.dto;

import com.refrigerator.item.entity.Item;
import com.refrigerator.itemCategory.entity.ItemCategory;
import com.refrigerator.state.entity.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateDto {

    @NotBlank
    private String name;
    
    private Long categoryId;

    @NotNull
    private Long stateId;

    // Converts ItemCreateDto to Item entity
    public Item toItem(ItemCategory category, State state) {
        Item item = new Item();
        item.setName(name);
        item.setCategory(category);
        item.setState(state);
        return item;
    }
}