package com.example.springapigateway.user.service;

import com.example.springapigateway.exception.CustomException;
import com.example.springapigateway.security.handler.AuthCode;
import com.example.springapigateway.user.dto.MemberDto;
import com.example.springapigateway.user.entity.Member;
import com.example.springapigateway.user.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService, UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new CustomException(AuthCode.UsernameOrPasswordNotFoundException));
    }

    @Override
    public MemberDto create(MemberDto memberDto) {
        memberRepository.findByEmail(memberDto.getEmail()).ifPresent(e -> {
            throw new CustomException(AuthCode.APPLYEMAIL);
        });
        Member user = memberRepository.save(memberDto.toEntity());

        return user.toDto();
    }

}
