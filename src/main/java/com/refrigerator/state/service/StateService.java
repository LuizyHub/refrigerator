package com.refrigerator.state.service;

import com.refrigerator.state.dto.StateCreateDto;
import com.refrigerator.state.dto.StateResponseDto;
import com.refrigerator.state.entity.State;
import com.refrigerator.state.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public StateResponseDto createState(StateCreateDto stateCreateDto) {
        State state = new State();
        state.setName(stateCreateDto.getName());
        state = stateRepository.save(state);
        return new StateResponseDto(state.getStateId(), state.getName());
    }

    public State getStateById(Long id) {
        return stateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("State not found"));
    }

    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    public State updateState(Long id, String name) {
        State state = getStateById(id);
        state.setName(name);
        return stateRepository.save(state);
    }

    public void deleteState(Long id) {
        stateRepository.deleteById(id);
    }
}