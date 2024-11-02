
// ItemResponseDto.java
package com.refrigerator.item.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDto {
    private Integer item_id;
    private String name;
    private Integer category_id; // Optional
    private Integer state_id;
}