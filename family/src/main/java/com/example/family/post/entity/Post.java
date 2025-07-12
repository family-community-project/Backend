package com.example.family.post.entity;

import com.example.family.like.entity.Like;
import com.example.family.reply.entity.Reply;
import com.example.family.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String category;
    private String title;
    private String contents;
    private String time;
    private Long likeCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> imageList;

    @OneToMany(mappedBy = "post")
    private List<Reply> replyList;

    @OneToMany(mappedBy = "post")
    private List<Like> likeList;

    public void addImage(Image image) {
        imageList.add(image);
    }
}
