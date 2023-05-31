package com.example.springuserservice.member.service;

import com.example.springuserservice.member.dto.MemberDto;
import com.example.springuserservice.member.entity.Member;
import com.example.springuserservice.member.handler.CustomException;
import com.example.springuserservice.member.handler.ErrorCode;
import com.example.springuserservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException());
    }

    @Override
    public MemberDto create(MemberDto memberDto) {
        memberRepository.findByEmail(memberDto.getEmail()).ifPresent(e -> {
            throw new CustomException(ErrorCode.REFISTEREDEMAIL);
        });
        Member user = memberRepository.save(memberDto.toEntity());

        return user.toDto();
    }



}
