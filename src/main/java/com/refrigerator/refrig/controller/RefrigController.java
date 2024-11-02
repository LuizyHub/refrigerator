package com.refrigerator.refrig.controller;

import com.refrigerator.common.resolver.CurrentUser;
import com.refrigerator.refrig.dto.*;
import com.refrigerator.refrig.service.RefrigService;
import com.refrigerator.user.controller.UserController;
import com.refrigerator.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refrigs")
@RequiredArgsConstructor
public class RefrigController {

    private final RefrigService refrigService;

    // 1. 냉장고 추가
    @PostMapping
    public ResponseEntity<RefrigResponseDto> addRefrig(
            @RequestBody RefrigRequestDto refrigRequestDto,
            @CurrentUser User user) {
        RefrigResponseDto responseDto = refrigService.addRefrig(refrigRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 2. 냉장고 수정
    @PutMapping("/{refrig_id}")
    public ResponseEntity<Void> updateRefrig(@PathVariable Long refrig_id,
                                             @RequestBody RefrigRequestDto refrigRequestDto) {
        refrigService.updateRefrig(refrig_id, refrigRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 3. 냉장고 삭제
    @DeleteMapping("/{refrig_id}")
    public ResponseEntity<Void> deleteRefrig(@PathVariable Long refrig_id) {
        refrigService.deleteRefrig(refrig_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
