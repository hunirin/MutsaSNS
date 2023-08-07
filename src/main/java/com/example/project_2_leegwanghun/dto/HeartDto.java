package com.example.project_2_leegwanghun.dto;

import lombok.Data;

@Data
public class HeartDto {
    private Long heartId;

    private Long userId;
    private Long articleId;

    public HeartDto(Long userId, Long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

}
