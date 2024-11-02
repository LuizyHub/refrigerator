// ItemRequestDto.java
package com.refrigerator.item.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {
    private String name;
    private Integer category_id; // Optional
    private Integer state_id;
}