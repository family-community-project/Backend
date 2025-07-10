package com.example.family.post.entity;

import com.example.family.like.entity.Like;
import com.example.family.reply.entity.Reply;
import com.example.family.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String subject;
    private String postContents;
    private String time;
    private Long likeCount;

    @OneToMany(mappedBy = "post")
    private List<Reply> replyList;

    @OneToMany(mappedBy = "post")
    private List<Like> likeList;
}
