package com.example.family.post.controller;

import com.example.family.post.dto.request.CreatePostRequest;
import com.example.family.post.dto.response.CreatePostResponse;
import com.example.family.post.repository.PostRepository;
import com.example.family.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping("/create")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {

        if (postService.createPost(request)) {
            return ResponseEntity.ok(new CreatePostResponse(true));
        } else {
            return ResponseEntity.ok(new CreatePostResponse(false));
        }
    }
}
