package com.refrigerator.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;
import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(LoginException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/login"));

        return ResponseEntity.status(HttpStatus.SEE_OTHER)  // 303 See Other
                .headers(headers)
                .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException(UnauthorizedException e, Model model) {
        model.addAttribute("errorTitle", "Forbidden");
        model.addAttribute("errorMessage", e.getMessage());
        return "error/403";  // 403.html 페이지로 이동
    }

    // 기타 예외 처리 메서드...
}
