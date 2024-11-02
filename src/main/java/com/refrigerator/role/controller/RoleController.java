package com.refrigerator.role.controller;

import com.refrigerator.refrig.dto.RoleRequestDto;
import com.refrigerator.refrig.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // 1. 타 사용자에게 냉장고 권한 부여
    @PostMapping
    public ResponseEntity<Void> addRole(@RequestBody RoleRequestDto roleRequestDto) {
        roleService.addRole(roleRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 2. 타 사용자에게 냉장고 권한 수정
    @PutMapping
    public ResponseEntity<Void> updateRole(@RequestBody RoleRequestDto roleRequestDto) {
        roleService.updateRole(roleRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
