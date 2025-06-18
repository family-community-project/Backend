package com.example.family.user.entity;

import com.example.family.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    private String name;
    private String password; // 어떻게 보관해야하나?
    private String accessToken;
    private String refreshToken;
    private String gender;
    private int age;

    private String profileComment;
    private String profileImage;
    private String familyType;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;
}
