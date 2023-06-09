package com.example.springuserservice.member.service;

import com.example.springuserservice.member.dto.MemberDto;
import com.example.springuserservice.member.dto.PostDto;
import com.example.springuserservice.member.entity.Member;
import com.example.springuserservice.member.handler.CustomException;
import com.example.springuserservice.member.handler.ErrorCode;
import com.example.springuserservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {
    private final Environment environment;
    private final RestTemplate restTemplate;
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

    @Override
    public MemberDto getMemberByUserId(Long userId) {
        MemberDto memberDto = memberRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(ErrorCode.NOTFOUND)
        ).toDto();

        ResponseEntity<List<PostDto>> postResponse = restTemplate.exchange(
                getPostRequestUrl(memberDto.getEmail()),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PostDto>>() {}
        );
        memberDto.setPosts(postResponse.getBody());

        return memberDto;
    }

    private String getPostRequestUrl(String email) {
        StringBuilder urlPrefix = new StringBuilder(Objects.requireNonNull(environment.getProperty("post.url-prefix")));
        urlPrefix.append(environment.getProperty("post.get-posts-path"));
        System.out.println("urlPrefix : " + urlPrefix.toString());
        System.out.println("result : " + String.format(urlPrefix.toString(), email));

        return String.format(urlPrefix.toString(), email);
    }


}
