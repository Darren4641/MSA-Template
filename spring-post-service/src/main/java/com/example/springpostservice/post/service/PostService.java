package com.example.springpostservice.post.service;

import com.example.springpostservice.post.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> findAll();

    PostDto save(PostDto postDto);

    List<PostDto> getPostByWriter(String writer);
}
