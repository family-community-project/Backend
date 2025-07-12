package com.example.family.post.service;

import com.example.family.post.dto.request.CreatePostRequest;
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

    public boolean createPost(CreatePostRequest request) {
        // user
        List<byte[]> imageList = request.getImage();

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
}
