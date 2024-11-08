package com.refrigerator.state.dto;

import lombok.Getter;

@Getter
public class StateResponseDto {

    // Getters
    private final Long stateId;
    private final String name;

    // Constructor
    public StateResponseDto(Long stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }
}