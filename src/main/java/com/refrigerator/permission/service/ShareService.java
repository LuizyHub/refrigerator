package com.refrigerator.permission.service;

import com.refrigerator.member.entity.Member;
import com.refrigerator.member.service.MemberService;
import com.refrigerator.permission.dto.ShareDto;
import com.refrigerator.permission.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final MemberRefrigService memberRefrigService;
    private final PermissionService permissionService;
    private final MemberService memberService;

    public Map<Member, Permission> getShareList(Long refrigId) {
        return memberRefrigService.getAllMemberRefrigByRefrigId(refrigId);
    }


    public void createShare(Long refrigId, ShareDto share) {
        Member member = memberService.findMemberByEmail(share.getEmail());

        Permission permission = permissionService.getPermission(share.getReadable(), share.getWritable(), share.getDeletable());

        memberRefrigService.createMemberRefrigRWD(member.getUserId(), refrigId, permission);


    }
}
