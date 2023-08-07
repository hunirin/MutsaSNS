package com.example.project_2_leegwanghun.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // unique 시 sql 에러발생
    private String username;

    @Column(nullable = false)
    private String password;

    private String profileImg;
    private String email;
    private String phone;

    private String token;

    @OneToMany(mappedBy = "user")
    private List<ArticleEntity> articles = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments = new ArrayList<>();
}
