package com.example.springpostservice.post.service;

import com.example.springpostservice.post.dto.PostDto;
import com.example.springpostservice.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public List<PostDto> findAll() {

        return postRepository.findAll().stream()
                .map(e -> e.toDto()).collect(Collectors.toList());
    }


}
