package com.refrigerator.member.controller;

import com.refrigerator.common.session.SessionConst;
import com.refrigerator.member.dto.*;
import com.refrigerator.member.entity.Member;
import com.refrigerator.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage(
            @ModelAttribute("user") MemberLoginDto memberLoginDto
    ) {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            Model model,
            @Valid @ModelAttribute("user") MemberLoginDto memberLoginDto,
            BindingResult bindingResult,
            HttpSession session
    ) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        Member user = memberService.login(memberLoginDto);
        if (user == null) {
            model.addAttribute("error", "존재하지 않는 사용자입니다.");
            return "login";
        }

        session.setAttribute(SessionConst.LOGIN_MEMBER_ID, user.getUserId());
        session.setAttribute(SessionConst.LOGIN_MEMBER_NAME, user.getName());

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(
            @ModelAttribute("user") MemberRegisterDto memberRegisterDto
    ) {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            Model model,
            @Valid @ModelAttribute("user") MemberRegisterDto memberRegisterDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            memberService.registerUser(memberRegisterDto);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "회원가입에 실패했습니다.");
            return "register";
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logoutUser(
            HttpSession session
    ) {
        // 세션에서 사용자 ID 제거
        session.removeAttribute(SessionConst.LOGIN_MEMBER_ID);
        session.removeAttribute(SessionConst.LOGIN_MEMBER_NAME);
        return "redirect:/";
    }
}
