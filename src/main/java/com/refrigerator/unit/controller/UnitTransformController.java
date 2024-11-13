package com.refrigerator.unit.controller;

import com.refrigerator.unit.dto.UnitCreateDto;
import com.refrigerator.unit.dto.UnitTransformCreateDto;
import com.refrigerator.unit.entity.Unit;
import com.refrigerator.unit.entity.UnitTransform;
import com.refrigerator.unit.service.UnitService;
import com.refrigerator.unit.service.UnitTransformService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/unitTransforms")
public class UnitTransformController {

    private final UnitTransformService unitTransformService;

    public UnitTransformController(UnitTransformService unitTransformService) {
        this.unitTransformService = unitTransformService;
    }

    // 모든 Unit 목록을 표시하는 페이지
    @GetMapping
    public String getUnitTransforms(Model model) {
        List<UnitTransform> unitTransforms = unitTransformService.getAllUnitTransforms();
        model.addAttribute("unitTransforms", unitTransforms);
        return "unitTransforms";
    }

    // 새 Unit 생성 폼 페이지
    @GetMapping("/new")
    public String createUnitForm(@ModelAttribute("unitsTransforms") UnitTransformCreateDto unitTransformCreateDto) {
        return "unitTransforms/new";
    }

    // 새 Unit 생성 처리
    @PostMapping("/new")
    public String createUnitTransform(
        @Valid @ModelAttribute("unit") UnitTransformCreateDto unitTransformCreateDto,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "unitTransforms/new";  // 유효성 검사 실패 시 다시 폼으로 이동
        }

        unitTransformService.createUnitTransform(unitTransformCreateDto);
        return "redirect:/unitTransforms";  // 목록 페이지로 리다이렉트
    }

    // 특정 Unit의 상세 페이지
    @GetMapping("/{fromUnitId}/{toUnitId}")
    public String getUnitTransformById(@PathVariable Integer fromUnitId, @PathVariable Integer toUnitId, Model model) {
        UnitTransform unitTransform = unitTransformService.getUnitTransformById(fromUnitId, toUnitId);
        model.addAttribute("unitTransform", unitTransform);
        return "unitTransforms/detail";  // units/detail.html 뷰로 이동
    }

    // 특정 Unit 삭제 처리
    @DeleteMapping("/{fromUnitId}/{toUnitId}/delete")
    public String deleteUnit(@PathVariable Integer fromUnitId, @PathVariable Integer toUnitId) {
        unitTransformService.deleteUnitTransform(fromUnitId, toUnitId);
        return "redirect:/unitTransforms";  // 목록 페이지로 리다이렉트
    }
}
