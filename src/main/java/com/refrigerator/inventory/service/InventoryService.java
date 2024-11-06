package com.refrigerator.inventory.service;

import com.refrigerator.common.exception.UnauthorizedException;
import com.refrigerator.permission.service.MemberRefrigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final MemberRefrigService memberRefrigService;

    public List getAllInventoriesReadable(Long userId, Long refrigId) {
        if (!memberRefrigService.hasPermissionToRead(userId, refrigId)) {
            throw new UnauthorizedException("해당 냉장고에 대한 읽기 권한이 없습니다.");
        }
        return List.of();
    }
}
