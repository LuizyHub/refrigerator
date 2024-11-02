package com.refrigerator.user.controller;

import com.refrigerator.user.dto.*;
import com.refrigerator.user.entity.User;
import com.refrigerator.user.service.UserService;
import com.refrigerator.common.exception.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 1. 사용자 등록
    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            userService.registerUser(userRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("duplicated email"));
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequestDto, WebRequest webRequest) {
        try {
            User user = userService.loginUser(loginRequestDto);
            // 세션에 사용자 ID 저장
            webRequest.setAttribute("userId", user.getUserId(), RequestAttributes.SCOPE_SESSION);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("no such email"));
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(WebRequest webRequest) {
        // 세션에서 사용자 ID 제거
        webRequest.removeAttribute("userId", RequestAttributes.SCOPE_SESSION);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
