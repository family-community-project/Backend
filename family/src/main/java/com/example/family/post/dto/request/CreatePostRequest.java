package com.example.family.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    String author;
    String title;
    String contents;
    List<byte[]> image;
}
