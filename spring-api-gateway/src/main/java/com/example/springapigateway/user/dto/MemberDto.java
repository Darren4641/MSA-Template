package com.example.springapigateway.user.dto;

import com.example.springapigateway.user.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberDto {
    private String email;
    private String password;
    private Role role;

    @Builder
    public MemberDto(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .role(role.getValue()).build();
    }
}
