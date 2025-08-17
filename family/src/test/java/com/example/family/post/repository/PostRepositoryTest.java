package com.example.family.post.repository;

import com.example.family.post.entity.Image;
import com.example.family.post.entity.Post;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void postCRTest() throws Exception {

        Image image1 = Image.builder()
                .image("23i324u".getBytes())
                .build();

        Image image2 = Image.builder()
                .image("23dfhsiru".getBytes())
                .build();

        // create
        Post post = Post.builder()
                .title("sample")
                .contents("contents")
                .imageList(new ArrayList<>())
                .build();

        post.addImage(image1);
        post.addImage(image2);
        post = postRepository.save(post);

        // read
        Post findPost = postRepository.findById(post.getId()).get();

        // assertion
        Assertions.assertThat(post.getId()).isEqualTo(findPost.getId());
    }
}