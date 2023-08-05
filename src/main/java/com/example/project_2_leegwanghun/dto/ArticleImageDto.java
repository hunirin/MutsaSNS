package com.example.project_2_leegwanghun.dto;

import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.ArticleImageEntity;
import com.example.project_2_leegwanghun.entity.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class ArticleImageDto {
    private Long id;

    private String imageUrl;

    public ArticleImageEntity newEntity(ArticleEntity article, UserEntity user) {
        ArticleImageEntity entity = new ArticleImageEntity();
        entity.setArticles(article);
        entity.setImageUrl(entity.getImageUrl());
        entity.setUser(user);
        return entity;
    }

    public static ArticleImageDto fromEntity(ArticleImageEntity image) {
        ArticleImageDto dto = new ArticleImageDto();
        dto.setId(image.getId());
        dto.setImageUrl(image.getImageUrl());
        return dto;
    }

    public static String getFirstImageUrl(List<ArticleImageDto> articleImages) {
        if (articleImages != null && !articleImages.isEmpty()) {
            return articleImages.get(0).getImageUrl();
        }
        return null;
    }
}
