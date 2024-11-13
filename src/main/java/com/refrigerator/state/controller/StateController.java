package com.refrigerator.state.controller;

import com.refrigerator.state.dto.StateCreateDto;
import com.refrigerator.state.entity.State;
import com.refrigerator.state.service.StateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    // 모든 State 리스트 페이지
    @GetMapping
    public String getStates(Model model) {
        List<State> states = stateService.getAllStates();
        model.addAttribute("states", states);
        return "states";  // states.html 뷰로 이동
    }

    // 새 State 생성 폼 페이지
    @GetMapping("/new")
    public String createStateForm(@ModelAttribute("state") StateCreateDto stateCreateDto) {
        return "states/new";  // states/new.html 뷰로 이동
    }

    // 새 State 생성 처리
    @PostMapping("/new")
    public String createState(
            @Valid @ModelAttribute("state") StateCreateDto stateCreateDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "states/new";  // 유효성 검사 실패 시 다시 폼으로 이동
        }

        stateService.createState(stateCreateDto);
        return "redirect:/states";  // 상태 목록 페이지로 리다이렉트
    }

    // 특정 State 상세 페이지
    @GetMapping("/{stateId}")
    public String getStateById(@PathVariable Long stateId, Model model) {
        State state = stateService.getStateById(stateId);
        model.addAttribute("state", state);
        return "states/detail";  // states/detail.html 뷰로 이동
    }

//    // 특정 State 업데이트 폼 페이지
//    @GetMapping("/{stateId}/edit")
//    public String editStateForm(@PathVariable Long stateId, Model model) {
//        State state = stateService.getStateById(stateId);
//        model.addAttribute("state", state);
//        return "states/edit";  // states/edit.html 뷰로 이동
//    }
//
//    // 특정 State 업데이트 처리
//    @PostMapping("/{stateId}/edit")
//    public String updateState(
//            @PathVariable Long stateId,
//            @Valid @ModelAttribute("state") StateCreateDto stateCreateDto,
//            BindingResult bindingResult
//    ) {
//        if (bindingResult.hasErrors()) {
//            return "states/edit";  // 유효성 검사 실패 시 다시 폼으로 이동
//        }
//
//        stateService.updateState(stateId, stateCreateDto);
//        return "redirect:/states";  // 상태 목록 페이지로 리다이렉트
//    }

    // 특정 State 삭제
    @PostMapping("/{stateId}/delete")
    public String deleteState(@PathVariable Long stateId) {
        stateService.deleteState(stateId);
        return "redirect:/states";  // 상태 목록 페이지로 리다이렉트
    }
}