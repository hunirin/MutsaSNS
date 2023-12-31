package com.example.project_2_leegwanghun.dto;

import com.example.project_2_leegwanghun.entity.ArticleImageEntity;
import lombok.Data;

import java.util.List;

@Data
public class ArticleImageDto {
    private Long id;

    private String imageUrl;


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
