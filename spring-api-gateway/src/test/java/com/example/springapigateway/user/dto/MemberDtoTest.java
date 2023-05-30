package com.example.springapigateway.user.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDtoTest {

    @Test
    public void lombok_test() {
        String email = "test_email";
        String password = "test_password";

        MemberDto dto = MemberDto.builder()
                .email(email)
                .password(password)
                .role(Role.USER).build();

        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getPassword()).isEqualTo(password);
    }

}
