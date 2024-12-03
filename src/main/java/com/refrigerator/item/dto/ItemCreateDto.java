package com.refrigerator.item.dto;

import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.state.entity.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateDto {

    @NotBlank(message = "아이템 이름은 필수 입력값입니다.")
    private String name;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long stateId;

    // Converts ItemCreateDto to Item entity
    public Item toItem(ItemCategory category, State state) {
        Item item = new Item(name, category, state);
        return item;
    }
}