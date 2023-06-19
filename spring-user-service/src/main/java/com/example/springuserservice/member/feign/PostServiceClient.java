package com.example.springuserservice.member.feign;

import com.example.springuserservice.member.dto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "post-service")
public interface PostServiceClient {
    @GetMapping("/second-service/post/{writer}/posts")
    List<PostDto> getPosts(@PathVariable String writer);
}
