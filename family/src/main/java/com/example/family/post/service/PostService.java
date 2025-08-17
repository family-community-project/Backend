package com.example.family.post.service;

import com.example.family.post.dto.request.CreatePostRequest;
import com.example.family.post.dto.request.UpdatePostRequest;
import com.example.family.post.entity.Image;
import com.example.family.post.entity.Post;
import com.example.family.post.repository.ImageRepository;
import com.example.family.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    // create
    public boolean createPost(CreatePostRequest request) {
        // user
        List<byte[]> imageList = request.getImages();

        Post post = Post.builder()
//                .user()
                .title(request.getTitle())
                .contents(request.getContents())
                .build();

        for (byte[] image : imageList) {
            Image createdImage = Image.builder()
                    .image(image)
                    .build();

            post.addImage(createdImage);
        }

        postRepository.save(post);

        return true;
    }

    // update
    public boolean updatePost(UpdatePostRequest request) {

        List<byte[]> imageList = request.getImages();

        Post post = Post.builder()
                .title(request.getTitle())
                .contents(request.getContents())
                .build();

        for (byte[] image : imageList) {
            Image updatedImage = Image.builder()
                    .image(image)
                    .build();

            post.addImage(updatedImage);
        }

        postRepository.save(post);

        return true;
    }

    public boolean deletePost(Long id) {

        postRepository.deleteById(id);
    }
}
