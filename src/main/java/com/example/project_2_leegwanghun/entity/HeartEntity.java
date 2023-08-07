package com.example.project_2_leegwanghun.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "heart")
@RequiredArgsConstructor
public class HeartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartId;

    // true = 좋아요, false = 좋아요 취소
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity articles;

    public HeartEntity(UserEntity user, ArticleEntity articles) {
        this.user = user;
        this.articles = articles;
        this.status = true;
    }

    public void unHeartEntity() {
        this.status = false;
    }



}
