package com.refrigerator.refrig.dto;

import com.refrigerator.refrig.entity.Refrigerator;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefrigeratorCreateDto {
    @NotBlank
    private String name;

    public Refrigerator toRefrigerator() {
        return Refrigerator.of(name);
    }
}
