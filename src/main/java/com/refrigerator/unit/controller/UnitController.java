package com.refrigerator.unit.controller;

import com.refrigerator.unit.dto.UnitCreateDto;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    // 모든 Unit 목록을 표시하는 페이지
    @GetMapping
    public String getUnits(Model model) {
        List<Unit> units = unitService.getAllUnits();
        model.addAttribute("units", units);
        return "units";  // units.html 뷰로 이동
    }

    // 새 Unit 생성 폼 페이지
    @GetMapping("/new")
    public String createUnitForm(@ModelAttribute("unit") UnitCreateDto unitCreateDto) {
        return "units/new";  // units/new.html 뷰로 이동
    }

    // 새 Unit 생성 처리
    @PostMapping("/new")
    public String createUnit(
        @Valid @ModelAttribute("unit") UnitCreateDto unitCreateDto,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "units/new";  // 유효성 검사 실패 시 다시 폼으로 이동
        }

        unitService.createUnit(unitCreateDto);
        return "redirect:/units";  // 목록 페이지로 리다이렉트
    }

    // 특정 Unit의 상세 페이지
    @GetMapping("/{unitId}")
    public String getUnitById(@PathVariable Integer unitId, Model model) {
        Unit unit = unitService.getUnitById(unitId);
        model.addAttribute("unit", unit);
        return "units/detail";  // units/detail.html 뷰로 이동
    }

    // 특정 Unit 삭제 처리
    @DeleteMapping("/{unitId}/delete")
    public String deleteUnit(@PathVariable Integer unitId) {
        unitService.deleteUnit(unitId);
        return "redirect:/units";  // 목록 페이지로 리다이렉트
    }
}
