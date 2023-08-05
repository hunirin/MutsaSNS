package com.example.project_2_leegwanghun.dto;

import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.ArticleImageEntity;
import com.example.project_2_leegwanghun.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    @JsonIgnore
    private String deletedAt;

    // 작성자 정보 저장용
    private String username;

    // 피드 전체 이미지
    @JsonIgnore
    private List<ArticleImageDto> articleImages;

    // 피드 대표 이미지
    private String articleImg;

    public ArticleEntity newEntity(UserEntity user) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(title);
        entity.setContent(content);
        entity.setDeletedAt(deletedAt);
        entity.setUser(user);
        return entity;
    }

    // 피드 목록용
    public static ArticleDto fromEntity(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setUsername(entity.getUser().getUsername());

        List<ArticleImageEntity> articleImages = entity.getArticleImages();
        if (articleImages != null && !articleImages.isEmpty()) {
            dto.setArticleImages(articleImages.stream().map(ArticleImageDto::fromEntity).collect(Collectors.toList()));
            dto.setArticleImg(dto.getArticleImages().get(0).getImageUrl());
        }
        return dto;
    }
    // 피드 개별용
    public static ArticleDto fromEntity2(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setUsername(entity.getUser().getUsername());

        List<ArticleImageEntity> articleImages = entity.getArticleImages();
        if (articleImages != null && !articleImages.isEmpty()) {
            dto.setArticleImages(articleImages.stream().map(ArticleImageDto::fromEntity).collect(Collectors.toList()));
        }
        return dto;
    }

    public void setArticleImages(List<ArticleImageDto> articleImages) {
        this.articleImages = articleImages;
        this.articleImg = ArticleImageDto.getFirstImageUrl(articleImages);
    }
}
