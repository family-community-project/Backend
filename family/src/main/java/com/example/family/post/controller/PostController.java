package com.example.family.post.controller;

import com.example.family.post.dto.request.CreatePostRequest;
import com.example.family.post.dto.response.CreatePostResponse;
import com.example.family.post.repository.PostRepository;
import com.example.family.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    // 게시글 조회 기준 ,,, (전체, 사람, ...)

    @GetMapping("/list")
    public ResponseEntity<?> searchPost() {

    }

    @GetMapping("/one")
    public ResponseEntity<?> getPost(@RequestParam Long id) {
        
    }

    @PostMapping("/create")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {

        if (postService.createPost(request)) {
            return ResponseEntity.ok(new CreatePostResponse(true));
        } else {
            return ResponseEntity.ok(new CreatePostResponse(false));
        }
    }

    @PostMapping("/update")
    public  ResponseEntity<CreatePostResponse> updatePost(@RequestBody CreatePostRequest request) {

    }
}
