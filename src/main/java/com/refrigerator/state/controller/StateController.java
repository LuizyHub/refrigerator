package com.refrigerator.state.controller;

import com.refrigerator.state.dto.StateCreateDto;
import com.refrigerator.state.dto.StateResponseDto;
import com.refrigerator.state.entity.State;
import com.refrigerator.state.service.StateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping
    public StateResponseDto createState(@RequestBody StateCreateDto name) {
        return stateService.createState(name);
    }

    @GetMapping("/{id}")
    public State getStateById(@PathVariable Long id) {
        return stateService.getStateById(id);
    }

    @GetMapping
    public List<State> getAllStates() {
        return stateService.getAllStates();
    }

    @PutMapping("/{id}")
    public State updateState(@PathVariable Long id, @RequestBody String name) {
        return stateService.updateState(id, name);
    }

    @DeleteMapping("/{id}")
    public void deleteState(@PathVariable Long id) {
        stateService.deleteState(id);
    }
}
