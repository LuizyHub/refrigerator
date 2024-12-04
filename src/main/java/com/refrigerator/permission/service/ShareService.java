package com.refrigerator.permission.service;

import com.refrigerator.member.entity.Member;
import com.refrigerator.permission.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final MemberRefrigService memberRefrigService;
    private final PermissionService permissionService;

    public Map<Member, Permission> getShareList(Long refrigId) {
        return memberRefrigService.getAllMemberRefrigByRefrigId(refrigId);
    }
}
