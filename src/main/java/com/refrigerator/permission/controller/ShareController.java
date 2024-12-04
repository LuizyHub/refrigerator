package com.refrigerator.permission.controller;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.member.entity.Member;
import com.refrigerator.permission.dto.ShareDto;
import com.refrigerator.permission.entity.Permission;
import com.refrigerator.permission.service.MemberRefrigService;
import com.refrigerator.permission.service.ShareService;
import com.refrigerator.refrig.service.RefrigeratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/new")
    public String createShareForm(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            @ModelAttribute("share") ShareDto shareDto,
            Model model
    ) {
        if (!memberRefrigService.hasOwnerPermission(member.getUserId(), refrigId)) {
            return "redirect:/refrigerators";
        }

        model.addAttribute("refrigerator", refrigeratorService.getRefrigeratorById(refrigId));
        return "share/new";
    }

    @PostMapping("/new")
    public String createShare(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            @Valid @ModelAttribute("share") ShareDto shareDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (!memberRefrigService.hasOwnerPermission(member.getUserId(), refrigId)) {
            return "redirect:/refrigerators";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("refrigerator", refrigeratorService.getRefrigeratorById(refrigId));

            return "share/new";
        }

        shareService.createShare(refrigId, shareDto);
        return "redirect:/refrigerators/{refrigId}/share";
    }
}

