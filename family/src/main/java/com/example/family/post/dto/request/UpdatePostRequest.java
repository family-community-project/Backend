package com.example.family.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdatePostRequest {
    String title;
    String contents;
    List<byte[]> images;
}
