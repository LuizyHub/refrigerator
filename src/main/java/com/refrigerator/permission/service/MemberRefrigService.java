package com.refrigerator.permission.service;

import com.refrigerator.permission.entity.MemberRefrig;
import com.refrigerator.permission.entity.Permission;
import com.refrigerator.permission.repository.MemberRefrigRepository;
import com.refrigerator.refrig.entity.Refrigerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberRefrigService {

    private final MemberRefrigRepository memberRefrigRepository;

    public boolean hasPermissionToRead(Long userId, Long refrigId) {
        return memberRefrigRepository.findByMemberUserIdAndRefrigeratorRefrigId(userId, refrigId)
                .map(memberRefrig -> memberRefrig.getPermission().isReadable())
                .orElse(false);
    }

    public boolean hasPermissionToWrite(Long userId, Long refrigId) {
        return memberRefrigRepository.findByMemberUserIdAndRefrigeratorRefrigId(userId, refrigId)
                .map(memberRefrig -> memberRefrig.getPermission().isWritable())
                .orElse(false);
    }

    public boolean hasPermissionToDelete(Long userId, Long refrigId) {
        return memberRefrigRepository.findByMemberUserIdAndRefrigeratorRefrigId(userId, refrigId)
                .map(memberRefrig -> memberRefrig.getPermission().isDeletable())
                .orElse(false);
    }

    public List<Refrigerator> getAllReadableRefrigerators(Long userId) {
        return memberRefrigRepository.findByMemberUserIdAndPermissionReadable(userId, true)
                .stream()
                .map(MemberRefrig::getRefrigerator)
                .toList();
    }

    public void createMemberRefrigRWD(Long userId, Long refrigId, Permission permission) {
        int i = memberRefrigRepository.insertMemberRefrig(userId, refrigId, permission.getPermissionId());
        if (i != 1) {
            throw new IllegalArgumentException("멤버 냉장고 생성에 실패했습니다.");
        }
    }
}
