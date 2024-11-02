package com.refrigerator.unit.controller;

import com.refrigerator.unit.dto.UnitResponseDto;
import com.refrigerator.unit.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    // 1. 단위 조회
    @GetMapping
    public ResponseEntity<List<UnitResponseDto>> getUnits(
            @RequestParam(required = false) Integer stateId) {
        List<UnitResponseDto> units = unitService.getUnits(stateId);
        return ResponseEntity.ok(units);
    }
}
