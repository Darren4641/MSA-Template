package com.example.springpostservice.post.controller;


import com.example.springpostservice.post.dto.CustomResponse;
import com.example.springpostservice.post.dto.PostDto;
import com.example.springpostservice.post.service.PostService;
import com.example.springpostservice.post.service.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/second-service/post")
public class PostController {
    private final PostService postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public CustomResponse getPostList() {
        return new CustomResponse.ResponseMap(200, "data", postService.findAll());
    }

    @PostMapping("/write")
    public CustomResponse writePost(@RequestBody PostDto postDto) {
        return new CustomResponse.ResponseMap(200, "data", postService.save(postDto));
    }

    @GetMapping("/{writer}/posts")
    public ResponseEntity<List<PostDto>> getPost(@PathVariable("writer") String writer) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByWriter(writer));
    }

}
