package com.example.springapigateway.user.controller;

import com.example.springapigateway.response.CustomResponse;
import com.example.springapigateway.user.dto.MemberDto;
import com.example.springapigateway.user.service.JwtService;
import com.example.springapigateway.user.service.MemberService;
import com.example.springapigateway.user.service.MemberServiceImpl;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService userService;
    private final JwtService jwtService;

    public UserController(PasswordEncoder passwordEncoder, MemberServiceImpl userService, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public CustomResponse signup(@RequestBody MemberDto memberDto) {
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return new CustomResponse.ResponseMap(200, "data", userService.create(memberDto));
    }

    @PostMapping("/login")
    public CustomResponse login(ServerHttpRequest request, @RequestBody MemberDto memberDto, @RequestHeader("User-Agent") String userAgent) {
        return jwtService.login(request, memberDto, userAgent);
    }




    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
