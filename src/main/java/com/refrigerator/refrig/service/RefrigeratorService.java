package com.refrigerator.refrig.service;

import com.refrigerator.permission.entity.Permission;
import com.refrigerator.permission.service.MemberRefrigService;
import com.refrigerator.permission.service.PermissionService;
import com.refrigerator.refrig.dto.RefrigeratorCreateDto;
import com.refrigerator.refrig.entity.Refrigerator;
import com.refrigerator.refrig.repository.RefrigeratorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RefrigeratorService {
    private final RefrigeratorRepository refrigeratorRepository;
    private final MemberRefrigService memberRefrigService;
    private final PermissionService permissionService;

    public void deleteRefrigerator(Long UserId, Long refrigId) {
        if (memberRefrigService.hasPermissionToDelete(UserId, refrigId)) {
            throw new IllegalArgumentException("해당 냉장고에 대한 권한이 없습니다.");
        }
        refrigeratorRepository.deleteById(refrigId);
    }

    public void createRefrigerator(Long userId, RefrigeratorCreateDto refrigeratorCreateDto) {
        Refrigerator save = refrigeratorRepository.save(refrigeratorCreateDto.toRefrigerator());

        Permission rwdPermission = permissionService.getRWDPermission();

        memberRefrigService.createMemberRefrigRWD(userId, save.getRefrigId(), rwdPermission);
    }

    public List<Refrigerator> getAllRefrigeratorsReadable(Long userId) {
        return memberRefrigService.getAllReadableRefrigerators(userId);
    }
}
