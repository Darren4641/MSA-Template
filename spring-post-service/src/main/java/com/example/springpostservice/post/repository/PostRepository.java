package com.example.springpostservice.post.repository;

import com.example.springpostservice.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();
    List<Post> findByWriter(String writer);

}
