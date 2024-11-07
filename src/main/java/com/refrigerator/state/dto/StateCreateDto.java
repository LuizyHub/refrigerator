package com.refrigerator.state.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StateCreateDto {

    private String name;

}