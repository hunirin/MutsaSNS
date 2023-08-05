package com.example.project_2_leegwanghun.entity;

import com.example.project_2_leegwanghun.dto.ArticleImageDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String deletedAt;

    // 작성자 정보 저장용
    private String username;

    // 피드 대표 이미지
    private String articleImg;

//    public void setArticleImages(List<ArticleImageEntity> articleImages) {
//        this.articleImages = articleImages;
//        this.articleImg = ArticleImageDto.getFirstImageUrl(articleImages);
//    }


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany
    @JoinColumn(name = "article")
    private List<ArticleImageEntity> articleImages = new ArrayList<>();


}
