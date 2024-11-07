package com.refrigerator.state.service;

import com.refrigerator.state.dto.StateCreateDto;
import com.refrigerator.state.entity.State;
import com.refrigerator.state.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    // State 엔티티로 반환하는 메서드
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    public State createState(StateCreateDto stateCreateDto) {
        State state = State.of(stateCreateDto.getName());
        return stateRepository.save(state);  // 생성된 State 반환
    }

    public Optional<State> getStateById(Long id) {
        return stateRepository.findById(id);
    }

    public State updateState(Long id, StateCreateDto stateCreateDto) {
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found"));
        state.updateName(stateCreateDto.getName());
        return stateRepository.save(state);  // 업데이트된 State 반환
    }

    public void deleteState(Long id) {
        stateRepository.deleteById(id);
    }
}