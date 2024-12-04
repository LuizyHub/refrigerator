package com.refrigerator.permission.controller;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.member.entity.Member;
import com.refrigerator.permission.entity.Permission;
import com.refrigerator.permission.service.MemberRefrigService;
import com.refrigerator.permission.service.ShareService;
import com.refrigerator.refrig.service.RefrigeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refrigerators/{refrigId}/share")
public class ShareController {

    private final ShareService shareService;
    private final MemberRefrigService memberRefrigService;
    private final RefrigeratorService refrigeratorService;

    @GetMapping
    public String getShareList(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            Model model
    ) {
        if (!memberRefrigService.hasOwnerPermission(member.getUserId(), refrigId)) {
            return "redirect:/refrigerators";
        }

        Map<Member, Permission> shareList = memberRefrigService.getAllMemberRefrigByRefrigId(refrigId);

        model.addAttribute("shareList", shareList);
        model.addAttribute("refrigerator", refrigeratorService.getRefrigeratorById(refrigId));

        return "share";
    }
}

