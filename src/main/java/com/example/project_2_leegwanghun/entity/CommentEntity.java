package com.example.project_2_leegwanghun.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    private String username;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity articles;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
