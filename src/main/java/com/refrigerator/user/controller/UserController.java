package com.refrigerator.user.controller;

import com.refrigerator.common.session.SessionConst;
import com.refrigerator.user.dto.*;
import com.refrigerator.user.entity.User;
import com.refrigerator.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(
            @ModelAttribute("user") UserLoginDto userLoginDto
    ) {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            Model model,
            @Valid @ModelAttribute("user") UserLoginDto userLoginDto,
            BindingResult bindingResult,
            HttpSession session
    ) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        User user = userService.login(userLoginDto);
        if (user == null) {
            model.addAttribute("error", "존재하지 않는 사용자입니다.");
            return "login";
        }

        session.setAttribute(SessionConst.LOGIN_USER_ID, user.getUserId());
        session.setAttribute(SessionConst.LOGIN_USER_NAME, user.getName());

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(
            @ModelAttribute("user") UserRegisterDto userRegisterDto
    ) {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            Model model,
            @Valid @ModelAttribute("user") UserRegisterDto userRegisterDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(userRegisterDto);
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
        session.removeAttribute(SessionConst.LOGIN_USER_ID);
        session.removeAttribute(SessionConst.LOGIN_USER_NAME);
        return "redirect:/";
    }
}
