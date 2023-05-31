package com.example.springpostservice.post.controller;


import com.example.springpostservice.post.dto.CustomResponse;
import com.example.springpostservice.post.service.PostService;
import com.example.springpostservice.post.service.PostServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-service")
public class PostController {
    private final PostService postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public CustomResponse getPostList() {
        return new CustomResponse.ResponseMap(200, "data", postService.findAll());
    }

}
