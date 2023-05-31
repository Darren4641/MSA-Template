package com.example.springuserservice.member.controller;

import com.example.springuserservice.member.dto.CustomResponse;
import com.example.springuserservice.member.dto.MemberDto;
import com.example.springuserservice.member.service.JwtService;
import com.example.springuserservice.member.service.MemberService;
import com.example.springuserservice.member.service.MemberServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/first-service")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public MemberController(MemberServiceImpl memberService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public CustomResponse signup(@RequestBody MemberDto memberDto) {
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return new CustomResponse.ResponseMap(200, "data", memberService.create(memberDto));
    }

    @PostMapping("/login")
    public CustomResponse login(HttpServletRequest request, @RequestBody MemberDto memberDto, @RequestHeader("User-Agent") String userAgent) {
        return new CustomResponse.ResponseMap(200, "data", jwtService.login(request, memberDto, userAgent));
    }
}
