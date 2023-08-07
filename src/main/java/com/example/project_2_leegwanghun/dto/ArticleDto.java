package com.example.project_2_leegwanghun.dto;

import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.ArticleImageEntity;
import com.example.project_2_leegwanghun.entity.CommentEntity;
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
    private boolean deletedAt;

    // 작성자 정보 저장용
    private String username;

    // 피드 전체 이미지
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ArticleImageDto> articleImages;

    // 피드 대표 이미지
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String articleImg;

    // 피드 삭제 이미지
    @JsonIgnore
    private List<Long> imagesToDelete;

    // 피드 댓글
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentDto> comments;

    // 피드 생성용
    public ArticleEntity newEntity(UserEntity user) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(title);
        entity.setContent(content);
        entity.setUser(user);
        return entity;
    }

    // 피드 목록용
    public static ArticleDto fromEntity(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setUsername(entity.getUser().getUsername());

        List<ArticleImageEntity> articleImages = entity.getArticleImages();
        if (articleImages != null && !articleImages.isEmpty()) {
            dto.setArticleImg(articleImages.stream().map(ArticleImageDto::fromEntity).collect(Collectors.toList()).get(0).getImageUrl());
        }
        return dto;
    }
    // 피드 개별용
    public static ArticleDto fromEntity2(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setUsername(entity.getUser().getUsername());
        // 이미지 전체 불러오기
        List<ArticleImageEntity> articleImages = entity.getArticleImages();
        if (articleImages != null && !articleImages.isEmpty()) {
            dto.setArticleImages(articleImages.stream().map(ArticleImageDto::fromEntity).collect(Collectors.toList()));
        }
        dto.setArticleImg(null);
        // 댓글 불러오기
        List<CommentEntity> comments = entity.getComments();
        if (comments != null && !comments.isEmpty()) {
            dto.setComments(comments.stream().map(CommentDto::fromEntity).collect(Collectors.toList()));
        }
        return dto;
    }

    public void setArticleImages(List<ArticleImageDto> articleImages) {
        this.articleImages = articleImages;
        this.articleImg = ArticleImageDto.getFirstImageUrl(articleImages);
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
