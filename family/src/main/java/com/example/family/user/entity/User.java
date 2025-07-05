package com.example.family.user.entity;

import com.example.family.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private Family family;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String name;
    private String password; // 어떻게 보관해야하나? 암호화 필요
    private String accessToken;
    private String refreshToken;

    private String profileComment;
    private String profileImage;

    private String familyType;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;
}
