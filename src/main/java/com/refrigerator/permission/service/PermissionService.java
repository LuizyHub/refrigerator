package com.refrigerator.permission.service;

import com.refrigerator.permission.entity.Permission;
import com.refrigerator.permission.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public Permission getRWDPermission() {
        return permissionRepository.findByName("RWD")
                .orElseThrow(() -> new IllegalArgumentException("RWD 권한이 존재하지 않습니다."));
    }
}
