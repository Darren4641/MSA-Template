package com.example.springuserservice.member.dto;


import lombok.Data;

@Data
public class PostDto {
    private String title;
    private String content;
    private String writer;
}
