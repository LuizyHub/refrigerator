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
        return getPermission("RWD");
    }

    public Permission getRWPermission() {
        return getPermission("RW");
    }

    public Permission getRPermission() {
        return getPermission("R");
    }

    public Permission getWPermission() {
        return getPermission("W");
    }

    public Permission getDPermission() {
        return getPermission("D");
    }

    public Permission getRDPermission() {
        return getPermission("RD");
    }

    public Permission getWDPermission() {
        return getPermission("WD");
    }

    public Permission getOwnerPermission() {
        return getPermission("OWNER");
    }

    private Permission getPermission(String name) {
        return permissionRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 권한이 존재하지 않습니다."));
    }

    public Permission getPermission(boolean readable, boolean writable, boolean deletable) {
        StringBuilder sb = new StringBuilder();

        if (readable) {
            sb.append("R");
        }
        if (writable) {
            sb.append("W");
        }
        if (deletable) {
            sb.append("D");
        }

        return getPermission(sb.toString());
    }
}
