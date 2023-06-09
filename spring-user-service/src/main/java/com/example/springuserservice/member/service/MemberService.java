package com.example.springuserservice.member.service;

import com.example.springuserservice.member.dto.MemberDto;
import com.example.springuserservice.member.dto.Token;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {
    MemberDto create(MemberDto memberDto);
    MemberDto getMemberByUserId(Long userId);
}
