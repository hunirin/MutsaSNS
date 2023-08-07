package com.example.project_2_leegwanghun.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "article")
@SQLDelete(sql = "UPDATE article SET deleted_at = true WHERE id = ?")
@Where(clause = "deleted_at=false")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    private boolean deletedAt= Boolean.FALSE;

    // 작성자 정보 저장용
    private String username;

    // 피드 대표 이미지
    private String articleImg;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleImageEntity> articleImages = new ArrayList<>();


}
