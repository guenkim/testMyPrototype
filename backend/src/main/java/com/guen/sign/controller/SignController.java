package com.guen.sign.controller;


import com.guen.common.model.dto.ApiResponse;
import com.guen.sign.dto.sign_in.request.SignInRequest;
import com.guen.sign.dto.sign_up.request.SignUpRequest;
import com.guen.sign.service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "회원 가입 및 로그인")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SignController {
    private final SignService signService;

    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    public ApiResponse signUp(@RequestBody SignUpRequest request) {
        return ApiResponse.success(signService.registMember(request));
    }

    @Operation(summary = "로그인")
    @PostMapping("/sign-in")
    public ApiResponse signIn(@RequestBody SignInRequest request) {
        return ApiResponse.success(signService.signIn(request));
    }

    @Operation(summary = "로그아웃")
    @DeleteMapping("/sign-out")
    public ApiResponse signOut(@AuthenticationPrincipal User user) {
        return ApiResponse.success(signService.signOut(UUID.fromString(user.getUsername())));
    }
}
